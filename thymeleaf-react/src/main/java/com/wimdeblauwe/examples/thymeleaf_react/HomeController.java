package com.wimdeblauwe.examples.thymeleaf_react;

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/timeline")
    public String timeline(Model model) {
        model.addAttribute("occurrences",
            List.of(
                new Occurrence(LocalDate.parse("2004-03-24"),
                    "Spring framework released",
                    "The first official version of the Spring framwork is released."),
                new Occurrence(LocalDate.parse("2014-04-01"),
                    "Spring Boot 1.0 released",
                    "Spring Boot brings a revolution to the Spring portfolio.")
                )
            );

        return "timeline";
    }

    public record Occurrence(LocalDate date, String title, String content) {

    }
}
