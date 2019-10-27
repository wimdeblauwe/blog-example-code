package com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa;

import java.io.Serializable;

/**
 * Interface for primary keys of entities.
 *
 * @param <T> the underlying type of the entity id
 */
public interface EntityId<T> extends Serializable {

    /**
     * Returns the underlying value of the entity id
     *
     * @return the value
     */
    T getValue();

    /**
     * Returns the value as a String that is suitable for use in a URL for example.
     *
     * @return a nice String representation of the value (as opposed to {@link #toString()} that is for debugging)
     */
    String asString();
}
