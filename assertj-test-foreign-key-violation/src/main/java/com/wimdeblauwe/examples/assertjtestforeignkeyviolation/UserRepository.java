package com.wimdeblauwe.examples.assertjtestforeignkeyviolation;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
