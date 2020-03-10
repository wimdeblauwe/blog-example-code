package com.wimdeblauwe.examples.laravelintermediatetasklist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}
