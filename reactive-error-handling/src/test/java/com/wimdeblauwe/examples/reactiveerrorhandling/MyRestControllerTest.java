package com.wimdeblauwe.examples.reactiveerrorhandling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(value = MyRestController.class)
@Import(UserService.class)
class MyRestControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testUserNotFound() {
        webTestClient.get()
                     .uri("/users/10")
                     .exchange()
                     .expectStatus().isNotFound()
                     .expectBody()
                     .consumeWith(System.out::println)
                     .jsonPath("$.code").isEqualTo("USER_NOT_FOUND")
                     .jsonPath("$.message").isEqualTo("No user found for id 10")
                     .jsonPath("$.userId").isEqualTo(10L);

    }
}
