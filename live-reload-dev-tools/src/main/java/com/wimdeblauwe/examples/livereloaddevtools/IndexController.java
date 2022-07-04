package com.wimdeblauwe.examples.livereloaddevtools;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexController {
    @GetMapping
    public String index() {
        return "index";
    }
}
