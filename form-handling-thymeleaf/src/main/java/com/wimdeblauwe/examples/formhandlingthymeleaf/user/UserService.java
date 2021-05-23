package com.wimdeblauwe.examples.formhandlingthymeleaf.user;

import java.util.List;

public interface UserService {
    User createUser(UserCreationParameters parameters);

    List<User> getUsers();
}
