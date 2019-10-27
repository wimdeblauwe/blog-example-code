package com.wimdeblauwe.examples.primarykeyobject.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Sql(statements = "CREATE SEQUENCE IF NOT EXISTS USER_SEQUENCE")
    public void testSaveUser() {
        User user = repository.save(new User("Wim"));

        entityManager.flush();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull()
                                .isInstanceOf(UserId.class);
        assertThat(user.getId().getValue()).isPositive();
    }
}
