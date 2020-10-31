package com.wimdeblauwe.examples.nestedtests.music.web;

import com.wimdeblauwe.examples.nestedtests.music.Album;
import com.wimdeblauwe.examples.nestedtests.music.MusicService;
import com.wimdeblauwe.examples.nestedtests.music.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicRestController {

    private final MusicService service;

    @Autowired
    public MusicRestController(MusicService service) {
        this.service = service;
    }

    @GetMapping("/songs")
    public List<Song> getSongs(@RequestParam("artist") String artist) {
        return service.findSongsByArtist(artist);
    }

    @GetMapping("/albums")
    public List<Album> getAlbums(@RequestParam("artist") String artist) {
        return service.findAlbumsByArtist(artist);
    }
}
