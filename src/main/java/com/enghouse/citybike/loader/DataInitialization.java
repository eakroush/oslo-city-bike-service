package com.enghouse.citybike.loader;

import com.enghouse.citybike.service.AvailabilityService;
import com.enghouse.citybike.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initial Data Loader triggered on Application Context Start Up
 *
 * @author Eimad.A
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class DataInitialization implements CommandLineRunner {

  private final StationService stationService;
  private final AvailabilityService availabilityService;

  @Override
  public void run(String... args) {

    log.info("Attempting To Load  Initial Stations Data...");
    this.stationService.pullAndIngestLiveStations();
    this.availabilityService.pullAndIngestLiveAvailabilities();
    log.info("Stations Initial  Data Successfully Loaded...");
  }
}
