package com.wimdeblauwe.examples.spendleaf.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(ProjectId id) {
        super("Project with id %s not found".formatted(id.getId()));
    }
}
