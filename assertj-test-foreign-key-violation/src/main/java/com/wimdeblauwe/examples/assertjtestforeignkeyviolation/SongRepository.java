package com.wimdeblauwe.examples.assertjtestforeignkeyviolation;

import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
