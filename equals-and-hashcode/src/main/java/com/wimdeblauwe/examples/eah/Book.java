package com.wimdeblauwe.examples.eah;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Book {
    @Id
    private Long id;

    private String name;

    protected Book() {
    }

    public Book(Long id,
                String name) {
        Assert.notNull(id, "id should not be null");
        Assert.notNull(name, "name should ot be null");
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
