package com.wimdeblauwe.examples.laravelintermediatetasklist.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String name, String email, String password) {
        return repository.save(new User(null, name, email, passwordEncoder.encode(password)));
    }

    @Override
    public Optional<User> getUser(int userId) {
        return repository.findById(userId);
    }
}
