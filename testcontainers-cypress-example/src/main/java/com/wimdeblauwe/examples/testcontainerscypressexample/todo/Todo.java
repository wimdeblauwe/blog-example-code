package com.wimdeblauwe.examples.testcontainerscypressexample.todo;

public class Todo {
    private String description;

    public Todo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
