package com.wimdeblauwe.examples.nestedtests.music;

public class Song {
    private final String artist;
    private final String title;

    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }
}
