package com.enghouse.citybike.service.impl;

import com.enghouse.citybike.integration.OsloCityBikeIntegrationService;
import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.model.StationAvailability;
import com.enghouse.citybike.repository.StationAvailabilityRepository;
import com.enghouse.citybike.service.AvailabilityService;
import com.enghouse.citybike.service.StationService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of the {@link StationService}
 *
 * @author Eimad.A
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultAvailabilityService implements AvailabilityService {

  private final OsloCityBikeIntegrationService integrationService;
  private final StationService stationService;
  private final StationAvailabilityRepository availabilityRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional // Transaction behavior required in case of rollback
  public boolean pullAndIngestLiveAvailabilities() {
    log.info("Attempting to pull & ingest the list of all Bike Stations Availabilities");

    // Get Live Stations
    List<StationAvailability> liveAvailabilities = this.getLiveAvailabilities();
    // Delete Existing
    availabilityRepository.deleteAll();
    // ingest new Stations
    availabilityRepository.saveAll(liveAvailabilities);

    log.info("Successfully pulled & ingested the list of all Bike Stations Availabilities");

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public List<StationAvailability> getAvailabilities(String stationName) {

    log.info(
        "Retrieving the [Offline pre-ingested] list of Stations Availabilities for station name [{}]",
        stationName);
    List<Station> stations = this.stationService.getStations(stationName);

    List<StationAvailability> availabilities =
        stations.stream()
            .map(
                station -> {
                  return availabilityRepository
                      .findByStationId(station.getStationId())
                      .orElseThrow(
                          () ->
                              new RuntimeException(
                                  "Station with id [" + station.getStationId() + "] Not exist"));
                })
            .collect(Collectors.toList());
    log.trace(
        "the Retrieved list of all  [pre-ingested] Bike Stations Availabilities [{}]",
        availabilities);
    log.info("Successfully retrieved the [pre-ingested] list of all Bike Stations Availabilities");

    return availabilities;
  }

  /** {@inheritDoc} */
  @Override
  public List<StationAvailability> getAvailabilities(Boolean isRenting) {
    log.info("Retrieving the [Offline pre-ingested] list of all Bike Stations Availabilities");

    List<StationAvailability> availabilities =
        ObjectUtils.isEmpty(isRenting)
            ? this.availabilityRepository.findAll()
            : this.availabilityRepository.findByIsRenting(isRenting);

    log.trace(
        "the Retrieved list of all  [Offline pre-ingested] Bike Stations [{}]", availabilities);

    log.info(
        "Successfully retrieved the [Offline pre-ingested] list of all Bike Stations Availabilities");
    return availabilities;
  }

  /** {@inheritDoc} */
  @Override
  public List<StationAvailability> getLiveAvailabilities() {

    log.info("Retrieving the [Live] list of all Bike Stations Availabilities");

    List<StationAvailability> availabilities = this.integrationService.getStationsAvailability();

    log.info("Successfully retrieved the [Live] list of all Bike Stations Availabilities");
    return availabilities;
  }
}
