package com.wimdeblauwe.examples.reactiveerrorhandling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class MyRestController {

    private final UserService service;

    public MyRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users/{id}")
    Mono<UserDto> findUser(@PathVariable("id") Long id) {
        return service.findUserById(id)
                      .map(user -> new UserDto(user.name()));
    }

    public record UserDto(String name) {
    }
}
