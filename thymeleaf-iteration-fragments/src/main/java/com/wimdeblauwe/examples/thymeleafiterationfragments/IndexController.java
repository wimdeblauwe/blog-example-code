package com.wimdeblauwe.examples.thymeleafiterationfragments;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", getUserList());
        return "index";
    }

    private List<User> getUserList() {
        return List.of(
                new User("Wim Deblauwe", "Belgium"),
                new User("Philip Riecks", "Germany")
        );
    }

    public static record User(String name, String country){}
}
