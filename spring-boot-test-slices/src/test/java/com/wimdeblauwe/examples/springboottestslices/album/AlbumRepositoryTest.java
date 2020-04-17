package com.wimdeblauwe.examples.springboottestslices.album;

import com.wimdeblauwe.examples.springboottestslices.infrastructure.test.MyAppDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MyAppDataJpaTest
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
}