package com.enghouse.citybike.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.enghouse.citybike.Application;
import com.enghouse.citybike.integration.OsloCityBikeIntegrationService;
import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.model.StationAvailability;
import com.enghouse.citybike.repository.StationRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StationServiceTest {

  @Autowired private StationService stationService;

  @Autowired private StationRepository stationRepository;

  private List<Station> dummyStations;

  @BeforeEach
  public void setup() {

    dummyStations = new ArrayList<>();
    dummyStations.add(
        Station.builder()
            .stationId(421L)
            .name("Alexander Kiellands Plas")
            .address("Alexander Kiellands Plas")
            .latitude(BigDecimal.valueOf(59.92806670615684))
            .longitude(BigDecimal.valueOf(10.751202636819613))
            .capacity(25)
            .build());

    dummyStations.add(
        Station.builder()
            .stationId(423L)
            .name("Tiedemannsparken")
            .address("TiedemannsparkenPlas")
            .latitude(BigDecimal.valueOf(59.92806670615684))
            .longitude(BigDecimal.valueOf(10.751202636819613))
            .capacity(35)
            .build());
  }

  @AfterEach
  public void tearDown() {
    stationRepository.deleteAll();
  }

  @Test
  public void whenGetLiveStations_isOk() {

    List<Station> stations = this.stationService.getLiveStations();
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
  public void whenGetOffLineStations_whenNoRecords_isOk() {

    List<Station> stations = this.stationService.getStations(null);
    assertNotNull(stations);
    assertTrue(stations.isEmpty());
  }

  @Test
  public void whenGetOffLineStations_RecordsExists_isOk() {

    this.stationRepository.saveAll(dummyStations);
    List<Station> stations = this.stationService.getStations(null);
    assertNotNull(stations);
    assertFalse(stations.isEmpty());

    assertEquals(2, stations.size());
  }

  @Test
  public void whenPullAndIngestLiveStations_noRecordIngested_isOk() {

    assertTrue(stationRepository.findAll().isEmpty());

    boolean isIngested = this.stationService.pullAndIngestLiveStations();
    assertTrue(isIngested);

    List<Station> stations = this.stationService.getStations(null);
    assertNotNull(stations);
    assertFalse(stations.isEmpty());

    assertTrue(stations.size() > 250);
  }


  @Test
  public void whenPullAndIngestLiveStations_RecordsIngested_isOk() {

    this.stationRepository.saveAll(dummyStations);
    assertFalse(stationRepository.findAll().isEmpty());

    boolean isIngested = this.stationService.pullAndIngestLiveStations();
    assertTrue(isIngested);

    List<Station> stations = this.stationService.getStations(null);
    assertNotNull(stations);
    assertFalse(stations.isEmpty());

    assertTrue(stations.size() > 250);

    stations = this.stationService.getStations("Kiellands Plas");
    assertNotNull(stations);
    assertFalse(stations.isEmpty());
    assertTrue(stations.size() > 0 );

  }
}
