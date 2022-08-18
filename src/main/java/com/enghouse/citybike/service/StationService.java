package com.enghouse.citybike.service;

import com.enghouse.citybike.model.Station;
import java.util.List;

/**
 * The interface Station Information service, that exposes the needed functionalities.
 *
 * @author Eimad.A
 */
public interface StationService {

  /**
   * Pull and ingest real time stations.
   *
   * @return the boolean
   */
  boolean pullAndIngestLiveStations();

  /**
   * Gets already ingested stations.
   *
   * @return the ingested stations
   */
  List<Station> getStations(String name);

  /**
   * Gets real time live stations.
   *
   * @return the real time stations
   */
  List<Station> getLiveStations();
}
