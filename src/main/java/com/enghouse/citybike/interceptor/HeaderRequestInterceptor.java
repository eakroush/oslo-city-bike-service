package com.enghouse.citybike.interceptor;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Header Enricher Interceptor
 *
 * @author Eimad.A
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

  private static final String ACCEPT_KEY = "Accept";
  private static final String CONTENT_TYPE_KEY = "Content-Type";
  private static final String JSON_FORMAT_VALUE = "application/json";

  private static final String CLIENT_IDENTIFIER_KEY = "Client-Identifier";
  private static final String CLIENT_IDENTIFIER_VALUE = "enghouse-systems";

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    // JSON Contents Request/Response
    request.getHeaders().set(ACCEPT_KEY, JSON_FORMAT_VALUE);
    request.getHeaders().set(CONTENT_TYPE_KEY, JSON_FORMAT_VALUE);

    // Client Identification [enghouse-systems]
    request.getHeaders().set(CLIENT_IDENTIFIER_KEY, CLIENT_IDENTIFIER_VALUE);

    return execution.execute(request, body);
  }
}
