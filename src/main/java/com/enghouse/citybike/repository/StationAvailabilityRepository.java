package com.enghouse.citybike.repository;

import com.enghouse.citybike.model.StationAvailability;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** The interface Station repository. */
@Repository
public interface StationAvailabilityRepository extends MongoRepository<StationAvailability, Long> {

  /**
   * Find by station id station availability.
   *
   * @param stationId the station id
   * @return the station availability
   */
  Optional<StationAvailability> findByStationId(Long stationId);

  /**
   * Find by name list.
   *
   * @param isRenting the is renting
   * @return the list
   */
  List<StationAvailability> findByIsRenting(Boolean isRenting);
}
