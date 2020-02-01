package com.wimdeblauwe.examples.testcontainerscypressexample.todo.web;

import com.wimdeblauwe.examples.testcontainerscypressexample.todo.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", service.findAll());
        return "todo-list";
    }
}
