package com.wimdeblauwe.examples.springboottestslices.album;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String artist;

    public Album() {
    }

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
