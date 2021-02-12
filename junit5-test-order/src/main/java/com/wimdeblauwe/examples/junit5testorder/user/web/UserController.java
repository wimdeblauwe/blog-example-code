package com.wimdeblauwe.examples.junit5testorder.user.web;

import com.wimdeblauwe.examples.junit5testorder.user.User;
import com.wimdeblauwe.examples.junit5testorder.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return repository.findById(id).orElseThrow();
    }
}
