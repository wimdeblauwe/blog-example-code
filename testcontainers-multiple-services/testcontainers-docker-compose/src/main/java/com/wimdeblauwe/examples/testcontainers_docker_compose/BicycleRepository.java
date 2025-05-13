package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface BicycleRepository extends ListCrudRepository<Bicycle, UUID> {
}
