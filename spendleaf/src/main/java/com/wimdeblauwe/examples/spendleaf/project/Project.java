package com.wimdeblauwe.examples.spendleaf.project;

import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Project extends AbstractEntity<ProjectId> {
    private String name;

    protected Project() {
    }

    public Project(ProjectId id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
