package com.wimdeblauwe.examples.spendleaf.infrastructure.web;

import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IdAndNameCollectionTest {
    private static final UUID ATLAS = UUID.randomUUID();
    private static final UUID BOREALIS = UUID.randomUUID();

    private final IdAndNameCollection collection = new IdAndNameCollection(List.of(
            new IdAndName(ATLAS, "Atlas"),
            new IdAndName(BOREALIS, "Borealis")));

    @Test
    void testNameOf() {
        assertThat(collection.nameOf(new ProjectId(BOREALIS))).isEqualTo("Borealis");
    }

    @Test
    void testNameOf_unknownId() {
        assertThat(collection.nameOf(new ProjectId(UUID.randomUUID()))).isNull();
    }

    @Test
    void testNameOf_null() {
        assertThat(collection.nameOf(null)).isNull();
    }

    @Test
    void testIterationKeepsTheOrder() {
        IdAndNameCollection reversed = new IdAndNameCollection(List.of(
                new IdAndName(BOREALIS, "Borealis"),
                new IdAndName(ATLAS, "Atlas")));

        assertThat(reversed).extracting(IdAndName::name).containsExactly("Borealis", "Atlas");
    }
}
