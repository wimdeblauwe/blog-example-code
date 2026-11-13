package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

/**
 * Stores a reference to a project, like {@code Expense.project}, as a UUID column.
 */
@Converter(autoApply = true)
class ProjectIdAttributeConverter implements AttributeConverter<ProjectId, UUID> {
    @Override
    public @Nullable UUID convertToDatabaseColumn(@Nullable ProjectId attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public @Nullable ProjectId convertToEntityAttribute(@Nullable UUID dbData) {
        return dbData != null ? new ProjectId(dbData) : null;
    }
}
