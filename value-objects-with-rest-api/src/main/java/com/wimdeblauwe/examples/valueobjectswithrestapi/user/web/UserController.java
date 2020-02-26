package com.wimdeblauwe.examples.valueobjectswithrestapi.user.web;

import com.wimdeblauwe.examples.valueobjectswithrestapi.user.UserId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("{id}")
    public UserInfo getUserInfo(@PathVariable("id") UserId userId) {
        return null;
    }
}
