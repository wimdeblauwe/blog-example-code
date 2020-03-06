package com.wimdeblauwe.examples.laravelintermediatetasklist.task.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateTaskParameters {
    @NotEmpty
    @Size(max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
