package com.wimdeblauwe.examples.datajpatests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("data-jpa-test")
class UserRepositoryTest {

    private final UserRepository repository;
    private final BookRepository bookRepository;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Autowired
    UserRepositoryTest(UserRepository repository,
                       BookRepository bookRepository,
                       JdbcTemplate jdbcTemplate,
                       EntityManager entityManager) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("UserRepositoryTest - BEFORE Users: " + repository.count());
        System.out.println("UserRepositoryTest - BEFORE Books: " + bookRepository.count());
//        System.out.println(jdbcTemplate.queryForObject("SELECT count(*) from \"user\"", Long.class));
    }

    @AfterEach
    public void afterEach() {
        // We need to clear the entity manager, otherwise, the unsaved instances are still managed by it
        // and we would get an exception.
//        entityManager.clear();
//        repository.deleteAll();
//        repository.deleteAllInBatch();
//        entityManager.flush();
        System.out.println("UserRepositoryTest - AFTER Users: " + repository.count());
        System.out.println("UserRepositoryTest - AFTER Books: " + bookRepository.count());
//        System.out.println(jdbcTemplate.queryForObject("SELECT count(*) from \"user\"", Long.class));
    }

    @Test
    void testSaveUser() {
        User user = new User();
        repository.save(user);

        entityManager.flush();

        assertThat(jdbcTemplate.queryForObject("SELECT id FROM my_user", Long.class)).isPositive();
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

        entityManager.clear();
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

        entityManager.clear();
    }
}
