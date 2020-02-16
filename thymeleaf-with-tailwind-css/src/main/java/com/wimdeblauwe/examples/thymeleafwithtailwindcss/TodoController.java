package com.wimdeblauwe.examples.thymeleafwithtailwindcss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class TodoController {
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", List.of(new Todo("Install Tailwind CSS"),
                                            new Todo("Make awesome UI")));
        return "index";
    }
}
