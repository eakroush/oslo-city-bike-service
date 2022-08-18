package com.enghouse.citybike.job;

import com.enghouse.citybike.model.StationAvailability;
import com.enghouse.citybike.service.AvailabilityService;
import com.enghouse.citybike.service.StationService;
import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * live Relaod Job job.
 *
 * @author Eimad.A
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class LiveDataReloadJob {

  /** The constant LIVE_RELOAD_CRON. */
  @VisibleForTesting static final String LIVE_RELOAD_CRON = "${service.data.reload.cron}";

  // Required dependencies on Construct time
  private final StationService stationService;
  private final AvailabilityService availabilityService;

  /** Handle Cinet Update Credentials Execution Ever on the 28 days after last update */
  @Scheduled(cron = LIVE_RELOAD_CRON)
  public void handleTasksExecution() {

    log.info("Starting Live Reload Data [Stations Oslo City Bike] Cron ");

    stationService.pullAndIngestLiveStations();
    availabilityService.pullAndIngestLiveAvailabilities();

    log.info("Finishing Live Reload Data [Stations Oslo City Bike] Cron ");
  }
}
