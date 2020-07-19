package com.wimdeblauwe.examples.errorhandling.inforequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InfoRequestNotFoundException extends RuntimeException {
    public InfoRequestNotFoundException(Long id) {
        super("There is no known info request with id " + id);
    }
}
