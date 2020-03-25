package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Song;
import com.voicetm.karaoke.dto.SongDto;
import com.voicetm.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class FrontSongRestController {
    private final SongService songService;

    @Autowired
    public FrontSongRestController(SongService songService){
        this.songService = songService;
    }

    @GetMapping
    public List<SongDto> songs(){
        return songService.getAllByActiveAndVideoIsNotNull(true);
    }

    @GetMapping("/top/{top}")
    public List<SongDto> topSongs(@PathVariable int top){
        return songService.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(true, top);
    }

    @GetMapping("/update-watch-count/{song}")
    public void updateWatchCount(@PathVariable Song song){
        if(song != null){
            song.setWatchedCounter(song.getWatchedCounter() + 1);
            songService.update(song);
        }
    }
}
