package com.wimdeblauwe.examples.reactiveerrorhandling;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/")
public class MyRestController {

    @GetMapping("/runtime")
    void throwRuntimeException() {
        throw new RuntimeException("This is a test RuntimeException");
    }

    @GetMapping("/bad-request")
    void throwExceptionWithBadRequestStatus() {
        throw new ExceptionWithBadRequestStatus();
    }

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody @Valid CreateUserRequest request) {
        return Mono.just(new UserDto());
    }

    public static class UserDto {
    }

    public static class CreateUserRequest {
        @NotBlank
        private String name;
        @Email
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
