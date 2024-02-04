package io.bootify.taming_thymeleaf.controller;

import io.bootify.taming_thymeleaf.model.AuthenticationRequest;
import io.bootify.taming_thymeleaf.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
            @RequestParam(name = "loginError", required = false) final Boolean loginError,
            @RequestParam(name = "logoutSuccess", required = false) final Boolean logoutSuccess,
            final Model model) {
        // dummy for using the inputRow fragment
        model.addAttribute("authentication", new AuthenticationRequest());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.login.required"));
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.login.error"));
        }
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.logout.success"));
        }
        return "authentication/login";
    }

}
