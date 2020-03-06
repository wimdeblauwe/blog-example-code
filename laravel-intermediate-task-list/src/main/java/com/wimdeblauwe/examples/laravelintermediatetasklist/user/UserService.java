package com.wimdeblauwe.examples.laravelintermediatetasklist.user;

public interface UserService {
    User createUser(String name, String email, String password);
}
