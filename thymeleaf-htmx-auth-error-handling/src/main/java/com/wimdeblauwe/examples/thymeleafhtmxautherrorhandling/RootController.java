package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.Instant;

@Controller
@RequestMapping
public class RootController {
    private final ChuckNorrisJokesApiClient apiClient;

    public RootController(ChuckNorrisJokesApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @HxRequest
    @GetMapping("/jokes/random")
    public String getRandomJoke(Model model) throws IOException {
        model.addAttribute("joke", apiClient.randomJoke().execute().body().value());
        return "fragments :: joke-content";
    }
}
