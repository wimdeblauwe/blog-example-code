package com.wimdeblauwe.examples.htmxiotdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/onload")
public class OnLoadController {

    private final IotDeviceService service;

    public OnLoadController(IotDeviceService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("devices", service.getDevices());
        return "onload/index";
    }

    @GetMapping("/{id}")
    public String deviceInfoPartial(@PathVariable("id") long id,
                                    Model model) {
        IotDevice device = service.getDevice(id);
        model.addAttribute("device", device);
        model.addAttribute("deviceTemperature", service.getTemperature(device));
        return "onload/partials :: device-info";
    }
}
