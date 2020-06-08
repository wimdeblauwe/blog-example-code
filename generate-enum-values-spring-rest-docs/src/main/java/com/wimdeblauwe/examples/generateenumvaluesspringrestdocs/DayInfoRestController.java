package com.wimdeblauwe.examples.generateenumvaluesspringrestdocs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class DayInfoRestController {

    @GetMapping("/dayinfo")
    public DayInfo getDayInfo() {
        return new DayInfo(LocalDate.parse("2020-07-15"), Season.SUMMER);
    }
}
