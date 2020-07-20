package com.wimdeblauwe.examples.errorhandling.inforequest;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
//@ResponseErrorCode("INFO_REQUEST_NOT_FOUND")
public class InfoRequestNotFoundException extends RuntimeException {
    private final Long infoRequestId;

    public InfoRequestNotFoundException(Long id) {
        super("There is no known info request with id " + id);
        this.infoRequestId = id;
    }

    @ResponseErrorProperty("id")
    public Long getInfoRequestId() {
        return infoRequestId;
    }
}
