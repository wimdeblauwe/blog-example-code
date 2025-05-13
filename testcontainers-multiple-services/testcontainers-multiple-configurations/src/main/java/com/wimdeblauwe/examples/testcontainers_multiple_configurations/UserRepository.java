package com.wimdeblauwe.examples.testcontainers_multiple_configurations;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {
}
