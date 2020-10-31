package com.wimdeblauwe.examples.nestedtests.music;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownArtistException extends RuntimeException {
    public UnknownArtistException(String artist) {
        super("Unknown artist: " + artist);
    }
}
