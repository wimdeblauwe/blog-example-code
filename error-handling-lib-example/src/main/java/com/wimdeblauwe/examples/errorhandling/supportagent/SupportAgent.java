package com.wimdeblauwe.examples.errorhandling.supportagent;

public class SupportAgent {
    private final Long id;
    private final String name;

    public SupportAgent(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
