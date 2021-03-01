package com.wimdeblauwe.examples.attributeconvertervsembeddable.attributeconverter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("acUserRepository")
public interface UserRepository extends CrudRepository<User, UserId> {

    Optional<User> findByPersonalEmail(Email email);

}
