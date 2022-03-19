package com.wimdeblauwe.examples.bootstraptoggleclone;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {
    private final SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    @GetMapping
    public String settingsPage(Model model) {
        Settings settings = service.getSettings();
        SettingsFormData formData = new SettingsFormData();
        formData.setNotifyViaEmail(settings.notifyViaEmail());
        formData.setNotifyViaSms(settings.notifyViaSms());
        model.addAttribute("settings", formData);

        return "settings";
    }

    @PostMapping
    public String updateSettings(@ModelAttribute("settings") SettingsFormData formData) {
        System.out.println("Got formdata: " + formData);
        service.setSettings(new Settings(formData.isNotifyViaEmail(), formData.isNotifyViaSms()));
        return "redirect:/";
    }
}
