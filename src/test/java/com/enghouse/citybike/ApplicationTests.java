package com.enghouse.citybike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.enghouse.citybike.config.CommonConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

/** The type Application tests. */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application.yaml")
class ApplicationTests {

  @Autowired
  @Qualifier("genericRestTemplate")
  private RestTemplate restTemplate;

  @Autowired private CommonConfig configuration;

  /** Context loads. */
  @Test
  void contextLoads() {
    Application.main(new String[] {});
    assertTrue(true, "If I run, Means I'm Okay!");
  }

  /** Test load configurations. */
  @Test
  public void testLoadConfigurations() {

    assertNotNull(restTemplate, "Generic RestTemplate is null");

    assertNotNull(configuration, "Configuration bean is null");
    assertEquals("https://gbfs.urbansharing.com", configuration.getCityBikeUrl());
    assertEquals(Integer.valueOf(60), configuration.getConnectionTimeOut());
  }
}
