package com.enghouse.citybike.events;

import com.enghouse.citybike.helper.SequenceGenerator;
import com.enghouse.citybike.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/** Mongo Model JPA Event Listener */
@Component
public class StationModelListener extends AbstractMongoEventListener<Station> {

  private final SequenceGenerator sequenceGenerator;

  /**
   * Instantiates a new Station model listener.
   *
   * @param sequenceGenerator the sequence generator
   */
  @Autowired
  public StationModelListener(SequenceGenerator sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Station> event) {

    event.getSource().setId(sequenceGenerator.generateSequence(Station.SEQUENCE_NAME));
  }
}
