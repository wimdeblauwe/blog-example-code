package io.bootify.taming_thymeleaf.model;

import java.util.List;


public class PaginationModel {

    private List<PaginationStep> steps;
    private String elements;

    public List<PaginationStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<PaginationStep> steps) {
        this.steps = steps;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(final String elements) {
        this.elements = elements;
    }

}
