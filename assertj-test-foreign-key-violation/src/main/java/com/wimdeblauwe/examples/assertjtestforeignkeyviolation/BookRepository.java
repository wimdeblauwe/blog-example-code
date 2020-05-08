package com.wimdeblauwe.examples.assertjtestforeignkeyviolation;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
