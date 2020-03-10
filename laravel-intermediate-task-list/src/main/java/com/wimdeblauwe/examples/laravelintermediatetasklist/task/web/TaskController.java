package com.wimdeblauwe.examples.laravelintermediatetasklist.task.web;

import com.wimdeblauwe.examples.laravelintermediatetasklist.infrastructure.security.ApplicationUserDetails;
import com.wimdeblauwe.examples.laravelintermediatetasklist.task.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                        Model model) {

        model.addAttribute("createTaskParameters", new CreateTaskParameters());
        model.addAttribute("tasks", service.getTasksByUser(userDetails.getId()));
        return "task/index";
    }

    @PostMapping
    public String store(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                        @Valid @ModelAttribute("createTaskParameters") CreateTaskParameters parameters,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createTaskParameters", parameters);
            return "task/index";
        }

        service.createTask(userDetails.getId(), parameters.getName());

        return "redirect:/tasks";
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("@taskServiceImpl.getTask(#taskId).orElse(null)?.user.id == #userDetails.id")
    public String destroy(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                          @PathVariable("taskId") Integer taskId) {

        service.deleteTask(taskId);
        return "redirect:/tasks";
    }
}
