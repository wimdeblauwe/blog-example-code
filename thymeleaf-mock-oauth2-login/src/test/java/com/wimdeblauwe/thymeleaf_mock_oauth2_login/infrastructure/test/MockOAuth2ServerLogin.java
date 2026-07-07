package com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test;

import com.nimbusds.jose.JOSEObjectType;
import com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.security.Role;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import no.nav.security.mock.oauth2.MockOAuth2Server;
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback;
import org.springframework.core.env.Environment;

/**
 * Performs a real OAuth2 authorization code login against the {@link MockOAuth2Server} so that
 * tests running with {@code WebEnvironment.RANDOM_PORT} have an authenticated session.
 *
 * <p>Registered as a bean by {@link MockOAuth2ServerInitializer}. The returned value is the {@code
 * JSESSIONID} of the authenticated session, to be passed as a cookie on subsequent requests:
 *
 * <pre>{@code
 * @Autowired private MockOAuth2ServerLogin mockOAuth2ServerLogin;
 *
 * String session = mockOAuth2ServerLogin.login("view-user", Role.VIEW);
 * client.get().uri("/print-jobs").cookie("JSESSIONID", session)...
 * }</pre>
 */
public class MockOAuth2ServerLogin {

  public static final String SESSION_COOKIE_NAME = "JSESSIONID";

  private static final String ISSUER_ID = "issuer1";
  private static final String CLIENT_ID = "printing-engine-backend-client";
  private static final long TOKEN_EXPIRY_SECONDS = 3600L;

  private final MockOAuth2Server mockOAuth2Server;
  private final Environment environment;

  public MockOAuth2ServerLogin(MockOAuth2Server mockOAuth2Server, Environment environment) {
    this.mockOAuth2Server = mockOAuth2Server;
    this.environment = environment;
  }

  public String loginUser() {
    return login("user", Role.USER);
  }

  public String loginAdmin() {
    return login("admin", Role.ADMIN);
  }

  public String login(String username, Role... roles) {
    List<String> keycloakRoles = Arrays.stream(roles).map(Role::getKeycloakRole).toList();
    mockOAuth2Server.enqueueCallback(
        new DefaultOAuth2TokenCallback(
            ISSUER_ID,
            username,
            JOSEObjectType.JWT.getType(),
            List.of(CLIENT_ID),
            Map.of("preferred_username", username, "realm_access", Map.of("roles", keycloakRoles)),
            TOKEN_EXPIRY_SECONDS));

    CookieManager cookieManager = new CookieManager();
    try (HttpClient httpClient =
        HttpClient.newBuilder()
            .cookieHandler(cookieManager)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build()) {
      HttpRequest request =
          HttpRequest.newBuilder(
                  URI.create(
                      "http://localhost:%d/oauth2/authorization/keycloak"
                          .formatted(localServerPort())))
              .GET()
              .build();
      httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException e) {
      throw new UncheckedIOException("OAuth2 login flow failed", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("OAuth2 login flow interrupted", e);
    }

    return cookieManager.getCookieStore().getCookies().stream()
        .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
        .map(HttpCookie::getValue)
        .findFirst()
        .orElseThrow(
            () -> new IllegalStateException("OAuth2 login did not result in a session cookie"));
  }

  private int localServerPort() {
    Integer port = environment.getProperty("local.server.port", Integer.class);
    if (port == null) {
      throw new IllegalStateException(
          "No local.server.port property found. Logging in with MockOAuth2ServerLogin requires"
              + " a running server, use @PrintingEngineSpringBootTest(webEnvironment ="
              + " WebEnvironment.RANDOM_PORT)");
    }
    return port;
  }
}
