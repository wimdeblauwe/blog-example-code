package com.wimdeblauwe.examples.laravelintermediatetasklist.user;

import java.util.Optional;

public interface UserService {
    User createUser(String name, String email, String password);

    Optional<User> getUser(int userId);
}
