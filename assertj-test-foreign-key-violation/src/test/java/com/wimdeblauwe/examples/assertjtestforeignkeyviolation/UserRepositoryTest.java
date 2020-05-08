package com.wimdeblauwe.examples.assertjtestforeignkeyviolation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("data-jpa-test")
class UserRepositoryTest {

    private final UserRepository repository;
    private final EntityManager entityManager;

    @Autowired
    UserRepositoryTest(UserRepository repository,
                       EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Test
    void testUnableToSaveUserIfBookNotInDatabase() {
        Book book = new Book();
        book.setTitle("AssertJ in action");
        User user = new User();
        user.setFavoriteBook(book);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> {
                    repository.save(user);
                    entityManager.flush();
                })
                .havingCause()
                .withMessageMatching(".*object references an unsaved transient instance.*User.favoriteBook.*");
    }

    @Test
    void testUnableToSaveUserIfSongNotInDatabase() {
        Song song = new Song();
        song.setTitle("Bee Gees - Stayin' Inside");
        User user = new User();
        user.setFavoriteSong(song);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> {
                    repository.save(user);
                    entityManager.flush();
                })
                .havingCause()
                .withMessageMatching(".*object references an unsaved transient instance.*User.favoriteSong.*");
    }
}