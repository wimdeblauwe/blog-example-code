package com.wimdeblauwe.examples.springboottestslices.album;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Tag("db-test")
@ActiveProfiles("data-jpa-test")
class AlbumRepositoryTest {

    private final AlbumRepository repository;

    @Autowired
    public AlbumRepositoryTest(AlbumRepository repository) {
        this.repository = repository;
    }

    @Test
    void testSaveAlbum() {
        Album album = repository.save(new Album("Master of Puppets", "Metallica"));
        assertThat(album).isNotNull()
                         .extracting(Album::getId)
                         .isInstanceOfSatisfying(Long.class,
                                                 id -> assertThat(id).isPositive());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ExecutorService executorService() {
            return Executors.newSingleThreadExecutor();
        }
    }
}