package com.wimdeblauwe.examples.primarykeyobject.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UserId> {
}
