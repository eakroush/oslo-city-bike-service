package com.enghouse.citybike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  Station Data BSON Model
 *
 *  @author Eimad.A
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("stations")
public class Station {

  /*
    _id field:
    In MongoDB, each document stored in a collection requires a unique _id field that acts as a primary key. \
    If an inserted document omits the _id field, the MongoDB driver automatically generates an ObjectId for the _id
    field.
  */

  /** The constant SEQUENCE_NAME. */
  @JsonIgnore @Transient public static final String SEQUENCE_NAME = "stations_sequence";

  @JsonIgnore
  @Id // Required Custom Sequence generator
  private Long id;

  @JsonProperty("station_id")
  @NotNull
  private Long stationId;

  @Indexed // Index the search name
  @NotEmpty
  private String name;

  @NotEmpty
  private String address;

  @JsonProperty("lat")
  @NotNull
  private BigDecimal latitude;

  @JsonProperty("lon")
  @NotNull
  private BigDecimal longitude;

  @NotNull
  private Integer capacity;
}
