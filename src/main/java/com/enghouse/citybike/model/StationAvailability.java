package com.enghouse.citybike.model;

import com.enghouse.citybike.helper.BooleanValueDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Station Docks Bike and dock availability Data BSON Model
 *
 * @author Eimad.A
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("stations_availabilities")
public class StationAvailability {

  /*
    _id field:
    In MongoDB, each document stored in a collection requires a unique _id field that acts as a primary key. \
    If an inserted document omits the _id field, the MongoDB driver automatically generates an ObjectId for the _id
    field.
  */

  /** The constant SEQUENCE_NAME. */
  @JsonIgnore @Transient public static final String SEQUENCE_NAME = "stations_availabilities_sequence";

  @JsonIgnore @Id // Required Custom Sequence generator
  private Long id;

  @JsonProperty("station_id")
  @NotNull
  private Long stationId;

  // is the station currently on the street
  @JsonProperty("is_installed")
  @JsonDeserialize(using = BooleanValueDeserializer.class)
  @NotNull
  private Boolean isInstalled;

  // is the station currently renting bikes (even if the station is empty,
  // it is set to allow rentals this value should be 1)
  @JsonProperty("is_renting")
  @JsonDeserialize(using = BooleanValueDeserializer.class)
  @NotNull
  private Boolean isRenting;

  //  the station accepting bike returns
  //  if a station is full but would allow a return if it was not full then this value should be 1)
  @JsonProperty("is_returning")
  @JsonDeserialize(using = BooleanValueDeserializer.class)
  @NotNull
  private Boolean isReturning;

  @JsonProperty("num_bikes_available")
  @NotNull
  private Integer nbrBikesAvailable;

  @JsonProperty("num_bikes_disabled")
  @NotNull
  private Integer nbrBikesDisabled;

  @JsonProperty("num_docks_available")
  @NotNull
  private Integer nbrDocksAvailable;

  @JsonProperty("num_docks_disabled")
  @NotNull
  private Integer nbrDocksDisabled;
}
