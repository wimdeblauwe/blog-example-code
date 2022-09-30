package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping
public class RootController {
    @GetMapping
    public String index() {
        return "index";
    }

    @HxRequest
    @GetMapping("/jokes/random")
    public String getRandomJoke(Model model) {
        model.addAttribute("joke", "random joke here - " + Instant.now());
        return "fragments :: joke-content";
    }
}
