package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.service.SongService;
import com.voicetm.karaoke.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static java.lang.Math.min;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;

@RestController
@RequestMapping("/api")
public class VideoPlayerController {
    private final VideoService videoService;

    @Value("${upload.path}")
    private String uploadPath;

    private final Long CHUNK_SIZE = 100000L;

    @Autowired
    public VideoPlayerController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/full-video/{name}/{extension}")
    public ResponseEntity<UrlResource> getFullVideo(
            @PathVariable String name,
            @PathVariable String extension) throws MalformedURLException {

        String videoPath = uploadPath + "/" + VideoService.VIDEO_PATH + "/";
        File videoFilePath = new File(videoPath + name + "." + extension);

        UrlResource video = new UrlResource("file:///" + videoFilePath.getPath());

        return ResponseEntity.status(PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(video);
    }

    @GetMapping("/video/{name}")
    public ResponseEntity<ResourceRegion> playVideo(
            @PathVariable String name,
            @RequestHeader HttpHeaders headers) throws IOException {

        String videoPath = uploadPath + "/" + VideoService.VIDEO_PATH + "/";
        UrlResource video = new UrlResource("file:///" + videoPath + name);
        ResourceRegion region = resourceRegion(video, headers);

        return ResponseEntity.status(PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    @GetMapping("/play-song/{name}")
    public ResponseEntity<ResourceRegion> playSong(
            @PathVariable String name,
            @RequestHeader HttpHeaders headers) throws IOException {

        String videoPath = uploadPath + "/" + SongService.SONG_PATH + "/";
        UrlResource video = new UrlResource("file:///" + videoPath + name);
        ResourceRegion region = resourceRegion(video, headers);

        return ResponseEntity.status(PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    @GetMapping("/play-song/{name}/{extension}")
    public ResponseEntity<ResourceRegion> getSong(
            @PathVariable String name,
            @PathVariable String extension,
            @RequestHeader HttpHeaders headers) throws IOException {

        String videoPath = uploadPath + "/" + SongService.SONG_PATH  + "/";
        UrlResource video = new UrlResource("file:///" + videoPath + name + "." + extension);
        ResourceRegion region = resourceRegion(video, headers);

        return ResponseEntity.status(PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    @GetMapping("/video/{name}/{extension}")
    public ResponseEntity<ResourceRegion> getVideo(
            @PathVariable String name,
            @PathVariable String extension,
            @RequestHeader HttpHeaders headers) throws IOException {

        String videoPath = uploadPath + "/" + VideoService.VIDEO_PATH + "/";
        UrlResource video = new UrlResource("file:///" + videoPath + name + "." + extension);
        ResourceRegion region = resourceRegion(video, headers);

        return ResponseEntity.status(PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
        Long contentLength = video.contentLength();
        HttpRange range = headers.getRange()
                .stream()
                .findFirst()
                .orElse(null);

        if (range != null) {
            long start = range.getRangeStart(contentLength);
            Long end = range.getRangeEnd(contentLength);
            long rangeLength = min(CHUNK_SIZE, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = min(CHUNK_SIZE, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
