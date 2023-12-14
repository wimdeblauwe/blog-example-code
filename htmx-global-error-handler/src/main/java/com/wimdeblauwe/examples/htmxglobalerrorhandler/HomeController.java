package com.wimdeblauwe.examples.htmxglobalerrorhandler;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/test")
    @HxRequest
    public String test() {
        return "fragments :: message";
    }

    @GetMapping("/test-exception")
    @HxRequest
    public String testException() {
        throw new RuntimeException("Fake exception");
    }

    @ExceptionHandler(Exception.class)
    public HtmxResponse handleError(Exception ex) {
        return HtmxResponse.builder()
                           .reswap(HtmxReswap.none())
                           .view(new ModelAndView("fragments :: error-message", Map.of("message", ex.getMessage())))
                           .build();
    }
}
