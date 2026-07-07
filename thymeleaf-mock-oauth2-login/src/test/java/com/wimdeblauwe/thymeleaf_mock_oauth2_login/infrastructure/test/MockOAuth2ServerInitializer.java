package com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import no.nav.security.mock.oauth2.MockOAuth2Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;

public class MockOAuth2ServerInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  public static final String MOCK_OAUTH_2_SERVER_BASE_URL = "mock-oauth2-server.baseUrl";

  private static final Logger logger = LoggerFactory.getLogger(MockOAuth2ServerInitializer.class);

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    var server = registerMockOAuth2Server(applicationContext);
    var baseUrl = server.baseUrl().toString().replaceAll("/$", "");

    TestPropertyValues.of(Map.of(MOCK_OAUTH_2_SERVER_BASE_URL, baseUrl))
        .applyTo(applicationContext);
  }

  private MockOAuth2Server registerMockOAuth2Server(
      ConfigurableApplicationContext applicationContext) {
    var server = new MockOAuth2Server();
    server.start();
    waitForServerReady(server);
    var genericApplicationContext = (GenericApplicationContext) applicationContext;
    genericApplicationContext.registerBean(MockOAuth2Server.class, () -> server);
    genericApplicationContext.registerBean(
        MockOAuth2ServerLogin.class,
        () -> new MockOAuth2ServerLogin(server, applicationContext.getEnvironment()));
    return server;
  }

  private void waitForServerReady(MockOAuth2Server server) {
    var url = server.wellKnownUrl("issuer1").toString();
    try {
      RetryTemplate retryTemplate =
          new RetryTemplate(
              RetryPolicy.builder()
                  .maxRetries(3)
                  .delay(Duration.ofSeconds(1))
                  .multiplier(2.0)
                  .build());
      retryTemplate.execute(
          () -> {
            pingWellKnownUrl(url);
            return null;
          });
    } catch (RetryException e) {
      logger.warn("MockOAuth2Server may not be fully ready at {}", url, e);
    }
  }

  private static void pingWellKnownUrl(String url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
    connection.setConnectTimeout(1000);
    connection.setReadTimeout(1000);
    connection.getInputStream().close();
    logger.info("MockOAuth2Server is ready at {}", url);
  }
}
