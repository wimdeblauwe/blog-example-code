package com.wimdeblauwe.examples.todomvcthymeleaf.todoitem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoItemController {

    @GetMapping
    public String index() {
        return "index";
    }
}
