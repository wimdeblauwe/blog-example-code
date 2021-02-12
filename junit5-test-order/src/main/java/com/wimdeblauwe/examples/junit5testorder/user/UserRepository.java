package com.wimdeblauwe.examples.junit5testorder.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
