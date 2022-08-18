package com.enghouse.citybike.controller;

import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.model.StationAvailability;
import com.enghouse.citybike.service.AvailabilityService;
import com.enghouse.citybike.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Station & Availibility exposed endpoint
 * @author Eimad.A
 */
@RequestMapping(value = "api/stations")
@RestController
@RequiredArgsConstructor
public class StationController {

  private final StationService stationService;
  private final AvailabilityService availabilityService;

  /**
   * Rest Resource that get the list of All stations
   *
   * @return list of all {}
   */
  @Operation(tags = "Station", description = "Get a list of all stations")
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public List<Station> getStations(@RequestParam(required = false) String name) {

    return stationService.getStations(name);
  }



  /**
   * Rest Resource that get the list of All stations availabilities
   *
   * @return list of all {}
   */
  @Operation(tags = "Station", description = "Get a list of all stations availabilities")
  @GetMapping(value = "{station-name}/availabilities", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public List<StationAvailability> getStationsAvailabilities(@PathVariable("station-name") String stationName) {

    return availabilityService.getAvailabilities(stationName);
  }

}
