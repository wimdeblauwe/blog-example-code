package com.wimdeblauwe.examples.nestedtests.music.web;

import com.wimdeblauwe.examples.nestedtests.music.Album;
import com.wimdeblauwe.examples.nestedtests.music.MusicService;
import com.wimdeblauwe.examples.nestedtests.music.Song;
import com.wimdeblauwe.examples.nestedtests.music.UnknownArtistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicRestController.class)
class MusicRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MusicService service;

    @Test
    void testFindSongsByArtist() throws Exception {
        when(service.findSongsByArtist("Metallica"))
                .thenReturn(List.of(new Song("Metallica", "For Whom the Bell Tolls"),
                                    new Song("Metallica", "Master of Puppets")));

        mockMvc.perform(get("/api/music/songs")
                                .param("artist", "Metallica"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)));

        verify(service, times(1))
                .findSongsByArtist(anyString());
    }

    @Test
    void testFindSongsByArtistIfNotFound() throws Exception {
        when(service.findSongsByArtist("Justin Bieber"))
                .thenThrow(new UnknownArtistException("Just Bieber"));

        mockMvc.perform(get("/api/music/songs")
                                .param("artist", "Justin Bieber"))
               .andExpect(status().isNotFound());

        verify(service, times(1))
                .findSongsByArtist(anyString());
    }

    @Test
    void testFindAlbumsByArtist() throws Exception {
        when(service.findAlbumsByArtist("Metallica"))
                .thenReturn(List.of(new Album("Metallica", "Ride the Lightning"),
                                    new Album("Metallica", "Metallica")));

        mockMvc.perform(get("/api/music/albums")
                                .param("artist", "Metallica"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)));

        verify(service, times(1))
                .findAlbumsByArtist(anyString());

    }

    @Test
    void testFindAlbumsByArtistIfNotFound() throws Exception {
        when(service.findAlbumsByArtist("Justin Bieber"))
                .thenThrow(new UnknownArtistException("Just Bieber"));

        mockMvc.perform(get("/api/music/albums")
                                .param("artist", "Justin Bieber"))
               .andExpect(status().isNotFound());

        verify(service, times(1))
                .findAlbumsByArtist(anyString());
    }
}
