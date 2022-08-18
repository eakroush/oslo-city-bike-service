package com.enghouse.citybike.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Response BSON Generic Model.
 *
 * @param <T> the type parameter
 *
 * @author Eimad.A
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

  @JsonProperty("last_updated")
  private Integer lastUpdatedDate;

  @JsonProperty("ttl")
  private Integer timeToLive;

  private String version;

  private DataDetails<T> data;

  /**
   * The type Data details.
   *
   * @param <R> the type parameter
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor(force = true)
  public static class DataDetails<R> {

    private List<R> stations;
  }
}
