package com.enghouse.citybike.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Open API UI Custom Config.
 *
 * @author Eimad.A
 */
@Configuration
public class OpenApiConfig {

  /**
   * Custom open api.
   *
   * @return the open api Config
   */
  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .components(new Components())
        .info(
            new Info()
                .title("Oslo City Bike Service API Specs")
                .description("Oslo City Bike Service REST API Specs Using OpenAPI 3."));
  }
}
