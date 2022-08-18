package com.enghouse.citybike.integration;

import static org.springframework.http.HttpMethod.GET;

import com.enghouse.citybike.config.CommonConfig;
import com.enghouse.citybike.model.CommonResponse;
import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.model.StationAvailability;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * The type Tracker integration service.
 *
 * @author Eimad.A
 */
@Service
@Log4j2
public class OsloCityBikeIntegrationService {

  private static final String BASE_URL = "/oslobysykkel.no";
  private static final String GET_STATIONS_URL = BASE_URL.concat("/station_information.json");
  private static final String GET_STATIONS_AVAILABILITY_URL = BASE_URL.concat("/station_status.json");

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final String cityBikeUrl;

  /**
   * Instantiates a new Tracker integration service.
   *
   * @param restTemplate the rest template
   * @param objectMapper the object mapper
   * @param commonConfig the common config
   */
  public OsloCityBikeIntegrationService(
      @Qualifier("genericRestTemplate") RestTemplate restTemplate,
      @Qualifier("nonNullObjectMapper") ObjectMapper objectMapper,
      CommonConfig commonConfig) {

    this.cityBikeUrl = commonConfig.getCityBikeUrl();
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  /**
   * Gets the list of real time stations.
   *
   * @return the stations List
   */
  public List<Station> getAllStations() {

    log.info("Getting the list of all stations from real-time Oslo City Bike");

    ResponseEntity<CommonResponse<Station>> responseEntity =
        this.sendRestRequest(GET_STATIONS_URL, GET, Station.class);

    List<Station> stations = this.extractBody(responseEntity);
    log.trace("retrieved list of stations [{}]", stations);

    log.info("Successfully retrieved list of all stations with size [{}]", stations.size());
    return stations;
  }

  /**
   * Gets the list of real time Bike and dock availability.
   *
   * @return the stations List
   */
  public List<StationAvailability> getStationsAvailability() {

    log.info("Getting the stations Bike and dock availability rom real-time Oslo City Bike");

    ResponseEntity<CommonResponse<StationAvailability>> responseEntity =
        this.sendRestRequest(GET_STATIONS_AVAILABILITY_URL, GET, StationAvailability.class);

    List<StationAvailability> stationAvailabilities = this.extractBody(responseEntity);
    log.trace("retrieved list of availability [{}]", stationAvailabilities);

    log.info(
        "Successfully retrieved stations Bike and dock availability with size [{}]",
        stationAvailabilities.size());
    return stationAvailabilities;
  }

  /**
   * Generic method to make REST request to Oslo City Biker
   *
   * @param <R> the type parameter
   * @param url the url
   * @param method the method
   * @param clazz the clazz
   * @param uriVariables the uri variables
   * @return response entity
   */
  <R> ResponseEntity<CommonResponse<R>> sendRestRequest(
      String url, HttpMethod method, Class<R> clazz, Object... uriVariables) {

    log.info(
        "Sending Rest API Request to url [{}], method [{}]", this.cityBikeUrl.concat(url), method);
    String ApiUrl = this.cityBikeUrl.concat(url);
    TypeFactory typeFactory = TypeFactory.defaultInstance();
    JavaType typedCommonResponse = typeFactory.constructParametricType(CommonResponse.class, clazz);

    try {
      ResponseEntity<CommonResponse<R>> response =
          this.restTemplate.exchange(
              ApiUrl,
              method,
              null,
              new ParameterizedTypeReference<CommonResponse<R>>() {},
              uriVariables);
      log.trace("Request to url [{}] successful with response [{}]", url, response);
      response =
          ResponseEntity.ok(objectMapper.convertValue(response.getBody(), typedCommonResponse));
      return response;
    } catch (RestClientException | IllegalArgumentException e) {
      log.error("Request to url [{" + ApiUrl + "}] failed", e);
      throw e;
    }
  }

  /**
   * Extract body list.
   *
   * @param <T> the type parameter
   * @param responseEntity the response entity
   * @return the list
   */
  <T> List<T> extractBody(ResponseEntity<CommonResponse<T>> responseEntity) {
    CommonResponse<T> commonResponse = responseEntity.getBody();
    if (commonResponse != null) {
      CommonResponse.DataDetails<T> data = commonResponse.getData();
      if (data != null) {
        return data.getStations();
      }
    }
    log.error("Could not extract body from response entity [{}]", responseEntity);
    throw new IllegalStateException("Could not extract body from response entity");
  }
}
