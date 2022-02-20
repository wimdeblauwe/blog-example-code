package com.wimdeblauwe.examples.requestparametersvalidation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskRestController {
    @GetMapping
    public List<Task> getTasks(@Valid GetTaskRequestParameters parameters) {
        return new ArrayList<>(); // Would get this from a service normally
    }

    /*
    public static class GetTaskRequestParameters {
        @Past
        @NotNull
        private Instant from;

        @PastOrPresent
        @NotNull
        private Instant to;

        public Instant getFrom() {
            return from;
        }

        public void setFrom(Instant from) {
            this.from = from;
        }

        public Instant getTo() {
            return to;
        }

        public void setTo(Instant to) {
            this.to = to;
        }
    }
*/
}
