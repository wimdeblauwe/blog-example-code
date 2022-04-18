package com.wimdeblauwe.examples.htmxiotdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("standard")
public class StandardThymeleafController {

    private final IotDeviceService service;

    public StandardThymeleafController(IotDeviceService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        List<IotDevice> devices = service.getDevices();
        model.addAttribute("devices", devices.stream().map(device -> DeviceWithTemperature.of(device, service.getTemperature(device))).toList());
        return "standard/index";
    }

    public record DeviceWithTemperature(long id, String name, double temperature) {
        public static DeviceWithTemperature of(IotDevice device,
                                               double temperature) {
            return new DeviceWithTemperature(device.id(), device.name(), temperature);
        }
    }
}
