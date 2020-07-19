package com.wimdeblauwe.examples.errorhandling.supportagent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SupportAgentNotFoundException extends RuntimeException {
    public SupportAgentNotFoundException(Long id) {
        super("There is no known support agent with id " + id);
    }
}
