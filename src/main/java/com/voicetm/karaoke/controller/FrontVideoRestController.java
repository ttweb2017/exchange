package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Video;
import com.voicetm.karaoke.dto.VideoDto;
import com.voicetm.karaoke.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FrontVideoRestController {
    private final VideoService videoService;

    @Autowired
    public FrontVideoRestController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public List<VideoDto> videos(){
        return videoService.getAllByActiveAndVideoIsNotNull(true);
    }

    @GetMapping("/top5-videos")
    public List<VideoDto> top5Videos(){
        return videoService.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(true, 5);
    }

    @GetMapping("/top10-videos")
    public List<VideoDto> top10Videos(){
        return videoService.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(true, 10);
    }

    @GetMapping("/top20-videos")
    public List<VideoDto> top20Videos(){
        return videoService.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(true, 20);
    }

    @GetMapping("/update-watch-count/{video}")
    public void updateWatchCount(@PathVariable Video video){
        if(video != null){
            video.setWatchedCounter(video.getWatchedCounter() + 1);
            videoService.save(video);
        }
    }
}
