package io.bootify.taming_thymeleaf.user.repos;

import io.bootify.taming_thymeleaf.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

}
