package com.wimdeblauwe.examples.eah;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    void testEquals() {
        Book book1 = new Book(1L, "Taming Thymeleaf");
        Book book2 = new Book(1L, "Taming Thymeleaf");

        assertThat(book1).isEqualTo(book2);
    }
}
