package com.wimdeblauwe.examples.springboottestslices.album;

import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
}
