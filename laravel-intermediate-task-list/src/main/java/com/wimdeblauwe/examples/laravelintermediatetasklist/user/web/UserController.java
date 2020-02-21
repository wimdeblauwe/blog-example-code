package com.wimdeblauwe.examples.laravelintermediatetasklist.user.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal Principal principal) {
        if (principal == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }
}
