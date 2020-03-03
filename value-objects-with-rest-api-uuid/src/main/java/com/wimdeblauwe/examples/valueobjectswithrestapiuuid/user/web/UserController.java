package com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user.web;

import com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user.UserId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("{id}")
    public UserInfo getUserInfo(@PathVariable("id") UserId userId) {
        return null;
    }
}
