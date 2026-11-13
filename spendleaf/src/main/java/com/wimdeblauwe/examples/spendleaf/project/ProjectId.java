package com.wimdeblauwe.examples.spendleaf.project;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class ProjectId extends AbstractEntityId<UUID> {

    protected ProjectId() {
    }

    public ProjectId(UUID id) {
        super(id);
    }
}
