package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.dto.SingerDto;
import com.voicetm.karaoke.dto.VideoDto;
import com.voicetm.karaoke.service.SingerService;
import com.voicetm.karaoke.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/singers")
public class FrontSingerRestController {
    private final SingerService singerService;
    private final VideoService videoService;

    @Autowired
    public FrontSingerRestController(SingerService singerService, VideoService videoService) {
        this.singerService = singerService;
        this.videoService = videoService;
    }

    @GetMapping
    public List<SingerDto> singers(){
        return singerService.getAllByActiveOrderByFirstNameAsc(true);
    }

    @GetMapping("/{singer}")
    public List<VideoDto> songsOfCurrentSinger(@PathVariable Singer singer){
        return videoService.getAllBySingerAndActiveAndVideoIsNotNull(singer);
    }
}
