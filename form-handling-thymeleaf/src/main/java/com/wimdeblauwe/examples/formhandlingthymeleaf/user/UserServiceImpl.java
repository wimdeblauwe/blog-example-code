package com.wimdeblauwe.examples.formhandlingthymeleaf.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(UserCreationParameters parameters) {
        User user = new User(parameters.getGivenName(), parameters.getFamilyName());
        return repository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }
}
