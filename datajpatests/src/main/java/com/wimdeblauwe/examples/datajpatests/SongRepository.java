package com.wimdeblauwe.examples.datajpatests;

import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
