package com.wimdeblauwe.thymeleaf_mock_oauth2_login;

import static com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test.MockOAuth2ServerInitializer.MOCK_OAUTH_2_SERVER_BASE_URL;
import static com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test.MockOAuth2ServerLogin.SESSION_COOKIE_NAME;

import com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test.MockOAuth2ServerInitializer;
import com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.test.MockOAuth2ServerLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(
    properties = {
        "spring.security.oauth2.client.provider.keycloak.issuer-uri=${"
            + MOCK_OAUTH_2_SERVER_BASE_URL
            + "}/issuer1"
    },
    webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig(initializers = {MockOAuth2ServerInitializer.class})
@AutoConfigureRestTestClient
@ActiveProfiles("integration-test")
class HomeControllerTest {

  @Autowired
  private RestTestClient client;

  @Autowired
  private MockOAuth2ServerLogin mockOAuth2ServerLogin;

  @Test
  void testNotLoggedIn() {
    client.get().uri("/").exchange().expectStatus().is3xxRedirection();
  }

  @Test
  void testLoggedIn() {
    String session = mockOAuth2ServerLogin.loginUser();
    client.get().uri("/")
        .cookie(SESSION_COOKIE_NAME, session).exchange().expectStatus().isOk();
  }
}