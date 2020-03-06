package com.wimdeblauwe.examples.laravelintermediatetasklist.task.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("createTaskParameters", new CreateTaskParameters());
        return "task/index";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("createTaskParameters") CreateTaskParameters parameters,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createTaskParameters", parameters);
            return "task/index";
        }

        // TODO store new task

        return "redirect:/tasks";
    }

    @DeleteMapping("/{taskId}")
    public String destroy(@PathVariable Integer taskId) {
        return null;
    }
}
