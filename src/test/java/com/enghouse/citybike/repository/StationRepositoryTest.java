package com.enghouse.citybike.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.enghouse.citybike.model.Station;
import com.enghouse.citybike.helper.SequenceGenerator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class StationRepositoryTest {

  @Autowired private StationRepository stationRepository;

  @Autowired private MongoTemplate mongoTemplate;

  @Autowired private SequenceGenerator sequenceGenerator;

  @Configuration
  @ComponentScan("com.enghouse.citybike")
  public static class SpringConfig implements WebMvcConfigurer {}

  @BeforeEach
  public void setup() {}

  @AfterEach
  public void tearDown() {
    stationRepository.deleteAll();
  }

  @Test
  public void test() {

    // need to set the id Manually as the interceptors don't work in testing mode
    Station station =
        Station.builder()
            .id(getStationId())
            .stationId(421L)
            .name("Alexander Kiellands Plas")
            .address("Alexander Kiellands Plas")
            .latitude(BigDecimal.valueOf(59.92806670615684))
            .longitude(BigDecimal.valueOf(10.751202636819613))
            .capacity(25)
            .build();

    mongoTemplate.save(station);

    assertThat(stationRepository.findAll()).isNotEmpty();
    assertEquals(1, stationRepository.findAll().size());

    boolean isExists = stationRepository.existsById(station.getId());
    assertTrue(isExists);

    Optional<Station> stationOpt = stationRepository.findById(station.getId());
    assertTrue(stationOpt.isPresent());
    assertEquals(stationOpt.get(), station);


    List<Station> stations = stationRepository.findByNameContaining("Kiellands Plas");
    assertFalse(stations.isEmpty());
    assertEquals(station, stations.get(0));


    stations = stationRepository.findByNameContaining("blaaaaa");
    assertTrue(stations.isEmpty());

  }

  private long getStationId() {
    return sequenceGenerator.generateSequence(Station.SEQUENCE_NAME);
  }
}
