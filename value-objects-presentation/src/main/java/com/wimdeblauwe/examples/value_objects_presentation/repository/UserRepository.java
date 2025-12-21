package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.User;
import com.wimdeblauwe.examples.value_objects_presentation.UserId;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UserId> {
}
