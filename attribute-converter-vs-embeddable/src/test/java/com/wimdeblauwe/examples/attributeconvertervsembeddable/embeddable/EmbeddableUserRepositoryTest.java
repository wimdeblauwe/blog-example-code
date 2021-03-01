package com.wimdeblauwe.examples.attributeconvertervsembeddable.embeddable;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmbeddableUserRepositoryTest {

    @Autowired
    private EmbeddableUserRepository repository;

    @Test
    void testSave() {
        repository.save(new EmbeddableUser(new UserId(1L),
                                           new NaturalPersonName("Wim", "Deblauwe"),
                                           new Email("wim.deblauwe@gmail.com"),
                                           new Email("wim.deblauwe@widit.be")));
    }

    @Test
    void testFindByEmail() {
        repository.save(new EmbeddableUser(new UserId(1L),
                                           new NaturalPersonName("Wim", "Deblauwe"),
                                           new Email("wim.deblauwe@gmail.com"),
                                           new Email("wim.deblauwe@widit.be")));

        Optional<EmbeddableUser> byEmail = repository.findByPersonalEmail(new Email("wim.deblauwe@gmail.com"));
        assertThat(byEmail).isPresent();
    }
}
