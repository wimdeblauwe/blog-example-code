package com.wimdeblauwe.examples.nestedtests.music;

import java.util.List;

public interface MusicService {
    List<Song> findSongsByArtist(String artist);

    List<Album> findAlbumsByArtist(String artist);
}
