package com.wimdeblauwe.examples.htmxiotdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class IndexController {

    private final IotDeviceService service;

    public IndexController(IotDeviceService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("devices", service.getDevices());
        return "index";
    }

    @GetMapping("/{id}")
    public String deviceInfoPartial(@PathVariable("id") long id,
                                    Model model) {
        IotDevice device = service.getDevice(id);
        model.addAttribute("device", device);
        model.addAttribute("deviceTemperature", service.getTemperature(device));
        return "partials :: device-info";
    }
}
