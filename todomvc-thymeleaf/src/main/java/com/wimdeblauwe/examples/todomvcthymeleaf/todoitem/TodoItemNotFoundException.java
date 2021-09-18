package com.wimdeblauwe.examples.todomvcthymeleaf.todoitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException(Long id) {
        super(String.format("TodoItem with id %s not found", id));
    }
}
