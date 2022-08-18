package com.enghouse.citybike.config;

import com.enghouse.citybike.interceptor.HeaderRequestInterceptor;
import com.enghouse.citybike.interceptor.LoggingInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Template Configuration.
 *
 * @author Eimad.A
 */
@Configuration
public class RestTemplateConfig {

  private final CommonConfig commonConfig;
  private final Integer api_timeout_milli_seconds;

  public RestTemplateConfig(CommonConfig commonConfig) {
    this.commonConfig = commonConfig;
    this.api_timeout_milli_seconds = Math.toIntExact(TimeUnit.SECONDS.toMillis(commonConfig.getConnectionTimeOut()));
  }

  /**
   * Non null object mapper object mapper.
   *
   * @return the object mapper
   */
  @Bean
  @Primary
  @Qualifier("nonNullObjectMapper")
  public ObjectMapper nonNullObjectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
  }

  /**
   * Method for REST template generation.
   *
   * @param objectMapper the nonNullObjectMapper used for converting objects
   * @return Generates REST template.
   */
  @Bean
  @Qualifier("genericRestTemplate")
  public RestTemplate genericRestTemplate(
      @Qualifier("nonNullObjectMapper") final ObjectMapper objectMapper) {

    RestTemplate restTemplate =
        this.buildGenericRestTemplate(
            objectMapper, this.api_timeout_milli_seconds, this.api_timeout_milli_seconds);

    restTemplate.getInterceptors().add(new HeaderRequestInterceptor());
    restTemplate.getInterceptors().add(new LoggingInterceptor("External"));

    return restTemplate;
  }

  /**
   * Helper method to set common attributes between different REST templates producers.
   *
   * @param objectMapper the ObjectMapper used for converting objects
   * @param connectTimeout the connect timeout
   * @param readTimeout the read timeout
   * @return REST template with default configurations like (timeout, interceptors, converters
   *     ...etc)
   */
  public RestTemplate buildGenericRestTemplate(
      final ObjectMapper objectMapper, Integer connectTimeout, Integer readTimeout) {

    SimpleClientHttpRequestFactory clientConfig = new SimpleClientHttpRequestFactory();
    clientConfig.setConnectTimeout(connectTimeout);
    clientConfig.setReadTimeout(readTimeout);

    RestTemplate restTemplate =
        new RestTemplate(new BufferingClientHttpRequestFactory(clientConfig));

    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper));
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

    return restTemplate;
  }
}
