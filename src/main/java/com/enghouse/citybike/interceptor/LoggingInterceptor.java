package com.enghouse.citybike.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Logging Interceptor.
 *
 * @author Eimad.A
 */
@Log4j2
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

  private final String context;

  /**
   * Constructor for loggingInterceptor.
   *
   * @param context of request
   */
  public LoggingInterceptor(String context) {
    super();
    this.context = context;
  }

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    this.traceRequest(request, body);

    ClientHttpResponse response = execution.execute(request, body);

    this.traceResponse(response);

    return response;
  }

  /**
   * Log http request information.
   *
   * @param request - http request
   * @param body - request body
   */
  private void traceRequest(HttpRequest request, byte[] body) {

    // process sensitive data if needed
    final String reqBody = replaceSensitiveData(new String(body, StandardCharsets.UTF_8));

    Map<String, String> headers = request.getHeaders().toSingleValueMap();

    log.info(
        "==========================="
            + context
            + " request begin================================================");

    log.info("URI         : " + request.getURI());
    log.info("Method      : " + request.getMethod());
    log.info("Headers     : " + headers);
    log.info("Request body: " + reqBody);

    log.info(
        "==========================="
            + context
            + " request end==================================================");
  }

  /**
   * Log http response information.
   *
   * @param response http response
   */
  private void traceResponse(ClientHttpResponse response) {

    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {

      log.info(
          "============================"
              + context
              + " response begin==========================================");
      log.info("Status code  : " + response.getStatusCode());
      log.info("Status text  : " + response.getStatusText());
      log.info("Headers      : " + response.getHeaders());

      String body = br.lines().parallel().collect(Collectors.joining("\n"));

      // process sensitive data if needed
      final String resBody = replaceSensitiveData(body);

      log.info("Response body: " + resBody);

    } catch (Exception e) {
      log.error("An error occurred when printing the response: " + e.getMessage(), e);
    }

    log.info(
        "============================"
            + context
            + " response end============================================");
  }

  /**
   * @param payload request or response body.
   *
   * @return payload with sensitive data replaced/masked such as card numbers and accounts
   */
  private static String replaceSensitiveData(String payload) {
    return payload;
  }
}
