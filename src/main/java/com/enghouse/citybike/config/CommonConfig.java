package com.enghouse.citybike.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Common Configuration.
 *
 * @author Eimad.A
 */
@ToString
@Component
@Getter
public class CommonConfig {

  @Value("${service.integration.oslo.city-bike.realtime.url:''}")
  private String cityBikeUrl;

  @Value("${service.integration.connection.timeout.seconds:60}")
  private Integer connectionTimeOut;
}
