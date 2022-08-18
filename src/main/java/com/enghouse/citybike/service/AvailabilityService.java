package com.enghouse.citybike.service;

import com.enghouse.citybike.model.StationAvailability;
import java.util.List;

/**
 * The interface Station Information service, that exposes the needed functionalities.
 *
 * @author Eimad.A
 */
public interface AvailabilityService {

  /**
   * Pull and ingest real time stations Availability.
   *
   * @return the boolean
   */
  boolean pullAndIngestLiveAvailabilities();

  /**
   * Gets availabilities.
   *
   * @param stationName the station name
   * @return the availabilities
   */
  List<StationAvailability> getAvailabilities(String stationName);

  /**
   * Gets already ingested stations.
   *
   * @param isRenting the is renting
   * @return the ingested stations
   */
  List<StationAvailability> getAvailabilities(Boolean isRenting);

  /**
   * Gets real time live stations.
   *
   * @return the real time stations
   */
  List<StationAvailability> getLiveAvailabilities();
}
