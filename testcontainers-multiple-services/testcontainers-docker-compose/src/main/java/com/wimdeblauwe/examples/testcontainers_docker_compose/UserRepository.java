package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {
}
