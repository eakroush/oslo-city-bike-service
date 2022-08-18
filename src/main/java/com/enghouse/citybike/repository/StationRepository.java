package com.enghouse.citybike.repository;

import com.enghouse.citybike.model.Station;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** The interface Station repository. */
@Repository
public interface StationRepository extends MongoRepository<Station, Long> {

  /**
   * Find by name list.
   *
   * @param stationName the station name
   * @return the list
   */
  List<Station> findByNameContaining(String stationName);
}
