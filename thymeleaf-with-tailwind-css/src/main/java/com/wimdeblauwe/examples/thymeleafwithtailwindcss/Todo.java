package com.wimdeblauwe.examples.thymeleafwithtailwindcss;

public class Todo {
    private final String description;

    public Todo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
