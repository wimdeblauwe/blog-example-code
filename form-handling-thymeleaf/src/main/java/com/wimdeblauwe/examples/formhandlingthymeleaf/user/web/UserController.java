package com.wimdeblauwe.examples.formhandlingthymeleaf.user.web;

import com.wimdeblauwe.examples.formhandlingthymeleaf.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("formData", new CreateUserFormData());
        return "users/create";
    }

    @PostMapping("/create")
    public String doCreateUser(@Valid @ModelAttribute("formData") CreateUserFormData formData,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        service.createUser(formData.toParameters());

        return "redirect:/users";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", service.getUsers());

        return "users/list";
    }
}
