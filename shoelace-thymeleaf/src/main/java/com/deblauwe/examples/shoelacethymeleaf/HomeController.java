package com.deblauwe.examples.shoelacethymeleaf;

import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import io.github.wimdeblauwe.hsbt.mvc.HxTrigger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.getDefault().create();

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/purchase")
    @HxRequest
    @HxTrigger("item-bought")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchase() {
        if (RANDOM_GENERATOR.nextBoolean()) {
            throw new RuntimeException("There was a problem registering the purchase!");
        }
    }
}
