package com.wimdeblauwe.examples.junit5testorder.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testUser() {
        User user = new User(1, "Wim");

        assertThat(user)
                .isNotNull()
                .satisfies(u -> {
                    assertThat(u.getId()).isEqualTo(1L);
                    assertThat(u.getName()).isEqualTo("Wim");
                });
    }
}
