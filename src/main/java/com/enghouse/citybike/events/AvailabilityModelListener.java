package com.enghouse.citybike.events;

import com.enghouse.citybike.helper.SequenceGenerator;
import com.enghouse.citybike.model.StationAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/** Mongo Model JPA Event Listener */
@Component
public class AvailabilityModelListener extends AbstractMongoEventListener<StationAvailability> {

  private final SequenceGenerator sequenceGenerator;

  /**
   * Instantiates a new Station Availability model listener.
   *
   * @param sequenceGenerator the sequence generator
   */
  @Autowired
  public AvailabilityModelListener(SequenceGenerator sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<StationAvailability> event) {

    event.getSource().setId(sequenceGenerator.generateSequence(StationAvailability.SEQUENCE_NAME));
  }
}
