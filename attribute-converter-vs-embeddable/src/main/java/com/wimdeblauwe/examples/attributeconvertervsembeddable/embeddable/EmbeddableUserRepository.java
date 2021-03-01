package com.wimdeblauwe.examples.attributeconvertervsembeddable.embeddable;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmbeddableUserRepository extends CrudRepository<EmbeddableUser, UserId> {
    Optional<EmbeddableUser> findByPersonalEmail(Email email);
}
