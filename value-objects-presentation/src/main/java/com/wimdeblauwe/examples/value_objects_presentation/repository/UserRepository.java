package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Email;
import com.wimdeblauwe.examples.value_objects_presentation.User;
import com.wimdeblauwe.examples.value_objects_presentation.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, UserId> {

  Optional<User> findByEmail(Email email);
}
