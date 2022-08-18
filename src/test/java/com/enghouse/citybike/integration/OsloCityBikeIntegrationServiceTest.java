package com.enghouse.citybike.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.enghouse.citybike.Application;
import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.model.StationAvailability;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OsloCityBikeIntegrationServiceTest {

  @Autowired private OsloCityBikeIntegrationService cityBikeIntegrationService;

  @Test
  public void whenGetAllStations_isOk() {

    List<Station> stations = this.cityBikeIntegrationService.getAllStations();
    assertNotNull(stations);
    assertFalse(stations.isEmpty());

    stations.forEach(
        station -> {
          assertNotNull(station.getStationId());
          assertNotNull(station.getAddress());
          assertNotNull(station.getCapacity());
        });
  }

  @Test
  public void whenGetStationsAvailability_isOk() {

    List<StationAvailability> stationAvailabilities =
        this.cityBikeIntegrationService.getStationsAvailability();
    assertNotNull(stationAvailabilities);
    assertFalse(stationAvailabilities.isEmpty());

    stationAvailabilities.forEach(
        stationAvailability -> {
          assertNotNull(stationAvailability.getStationId());

          assertNotNull(stationAvailability.getIsInstalled());
          assertNotNull(stationAvailability.getIsRenting());
          assertNotNull(stationAvailability.getIsReturning());

          assertNotNull(stationAvailability.getNbrBikesAvailable());
          assertNotNull(stationAvailability.getNbrDocksAvailable());
        });
  }
}
