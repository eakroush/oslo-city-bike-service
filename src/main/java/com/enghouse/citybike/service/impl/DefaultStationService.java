package com.enghouse.citybike.service.impl;

import com.enghouse.citybike.integration.OsloCityBikeIntegrationService;
import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.repository.StationRepository;
import com.enghouse.citybike.service.StationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
public class DefaultStationService implements StationService {

  private final OsloCityBikeIntegrationService integrationService;
  private final StationRepository stationRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional // Transaction behavior required in case of rollback
  public boolean pullAndIngestLiveStations() {
    log.info("Attempting to pull & ingest the list of all Bike Stations");

    // Get Live Stations
    List<Station> liveStations = this.getLiveStations();
    // Delete Existing
    stationRepository.deleteAll();
    // ingest new Stations
    stationRepository.saveAll(liveStations);

    log.info("Successfully pulled & ingested the list of all Bike Stations");

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public List<Station> getStations(String name) {
    log.info("Retrieving the [Offline pre-ingested] list of all Bike Stations");

    List<Station> allStations =
        StringUtils.isEmpty(name)
            ? this.stationRepository.findAll()
            : this.stationRepository.findByNameContaining(name);

    log.trace("the Retrieved list of all  [Offline pre-ingested] Bike Stations [{}]", allStations);

    log.info("Successfully retrieved the [Offline pre-ingested] list of all Bike Stations");
    return allStations;
  }

  /** {@inheritDoc} */
  @Override
  public List<Station> getLiveStations() {

    log.info("Retrieving the [Live] list of all Bike Stations");

    List<Station> allStations = this.integrationService.getAllStations();

    log.info("Successfully retrieved the [Live] list of all Bike Stations");
    return allStations;
  }
}
