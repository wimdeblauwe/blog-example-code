package com.wimdeblauwe.examples.formhandlingthymeleaf.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
