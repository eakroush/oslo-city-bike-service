package com.enghouse.citybike.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/** The type Boolean value deserializer. */
public class BooleanValueDeserializer extends JsonDeserializer<Boolean> {

  @Override
  public Boolean deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException {
    return jsonParser.getIntValue() == 1;
  }
}
