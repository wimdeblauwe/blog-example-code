package com.wimdeblauwe.examples.htmxglobalerrorhandler;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/test")
    @HxRequest
    public String test() {
        return "fragments :: message";
    }

    @GetMapping("/test-exception")
    @HxRequest
    public String testException() {
        throw new RuntimeException("Fake exception");
    }
}
