package com.wimdeblauwe.examples.spendleaf.infrastructure.web;

import io.github.wimdeblauwe.jpearl.EntityId;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Id and name pairs, for example the options of a combobox, that can also look up a name by its id.
 * Iterates in the order the values were given.
 */
public class IdAndNameCollection implements Iterable<IdAndName> {
    private final Map<UUID, IdAndName> values = new LinkedHashMap<>();

    public IdAndNameCollection(List<IdAndName> values) {
        for (IdAndName value : values) {
            this.values.put(value.id(), value);
        }
    }

    public @Nullable String nameOf(@Nullable EntityId<UUID> id) {
        if (id == null) {
            return null;
        }
        IdAndName value = values.get(id.getId());
        return value != null ? value.name() : null;
    }

    @Override
    public Iterator<IdAndName> iterator() {
        return values.values().iterator();
    }
}
