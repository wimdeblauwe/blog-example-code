package com.wimdeblauwe.examples.datajpatests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("data-jpa-test")
class BookRepositoryTest {

    private final BookRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final TestEntityManager entityManager;

    @Autowired
    BookRepositoryTest(BookRepository repository, JdbcTemplate jdbcTemplate, TestEntityManager entityManager) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("BEFORE Books: " + repository.count());
    }

    @BeforeEach
    public void afterEach() {
        System.out.println("AFTER Books: " + repository.count());
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setTitle("High-Performance Java Persistence");
        repository.save(book);

        entityManager.flush();

        assertThat(jdbcTemplate.queryForObject("SELECT id FROM book", Long.class)).isPositive();
        assertThat(jdbcTemplate.queryForObject("SELECT title FROM book", String.class)).isEqualTo("High-Performance Java Persistence");
    }

    @Test
    void testSaveBook2() {
        Book book = new Book();
        book.setTitle("High-Performance Java Persistence");
        repository.save(book);

        entityManager.flush();

        assertThat(jdbcTemplate.queryForObject("SELECT id FROM book", Long.class)).isPositive();
        assertThat(jdbcTemplate.queryForObject("SELECT title FROM book", String.class)).isEqualTo("High-Performance Java Persistence");
    }
}
