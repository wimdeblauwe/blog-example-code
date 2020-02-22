package com.wimdeblauwe.examples.laravelintermediatetasklist.task.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String index() {
        return "task/index";
    }

    @PostMapping
    public String store() {
        return null;
    }

    @DeleteMapping("/{taskId}")
    public String destroy(@PathVariable Integer taskId) {
        return null;
    }
}
