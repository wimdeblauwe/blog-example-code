package com.wimdeblauwe.examples.nestedtests.music;

public class Album {
    private final String artist;
    private final String title;

    public Album(String artist, String title) {
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
