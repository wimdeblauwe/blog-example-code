package com.wimdeblauwe.examples.tcl.tcl_sample_01;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRedirectView;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {

  @GetMapping("/{id}")
  public String showDeleteDialog(@PathVariable String id, Model model) {
    model.addAttribute("message", "Are you sure you want to delete user '%s'?".formatted(id));
    model.addAttribute("id", id);

    return "fragments/dialogs :: delete-dialog";
  }

  @HxRequest
  @DeleteMapping("/{id}")
  public HtmxRedirectView deleteUser(@PathVariable String id, Model model) {
    // Delete the user via a service or use case here

    return new HtmxRedirectView("/");
  }
}
