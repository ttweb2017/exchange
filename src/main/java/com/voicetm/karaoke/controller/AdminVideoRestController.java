package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Video;
import com.voicetm.karaoke.dto.VideoDto;
import com.voicetm.karaoke.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/video")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
public class AdminVideoRestController {
    private final VideoService videoService;

    @Autowired
    public AdminVideoRestController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping
    public List<VideoDto> videoList(){
        return videoService.getAll();
    }

    @PutMapping("/set-status/{video}/{status}")
    public ResponseEntity<?> updateSingerStatus(@PathVariable Video video, @PathVariable boolean status){
        if(video == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        video.setActive(status);
        videoService.updateStatus(video);

        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<?> addVideo(
            @ModelAttribute Video video,
            @RequestParam("imageFile") MultipartFile uploadImage,
            @RequestParam("videoFile") MultipartFile uploadVideo) {

        try {
            videoService.saveFile(video, uploadImage, false);
            videoService.saveFile(video, uploadVideo, true);
            videoService.save(video);

            return ResponseEntity.ok(videoService.getOneById(video.getId()));
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Video videoFromDb,
            @ModelAttribute Video video,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile
    ) {
        if(videoFromDb == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        if(video.getName().isEmpty()){
            video.setName(videoFromDb.getName());
        }

        if(video.getSinger() == null){
            video.setSinger(videoFromDb.getSinger());
        }

        if(imageFile == null || imageFile.isEmpty()){
            video.setImage(videoFromDb.getImage());
        }

        if(videoFile == null || videoFile.isEmpty()){
            video.setVideo(videoFromDb.getVideo());
        }

        video.setActive(videoFromDb.isActive());
        video.setAddedDateTime(videoFromDb.getAddedDateTime());

        try {
            videoService.saveFile(video, imageFile, false);
            videoService.saveFile(video, videoFile, true);

            if(imageFile != null && !imageFile.isEmpty()){
                videoService.deleteFile(videoFromDb.getImage());
            }

            if(videoFile != null && !videoFile.isEmpty()){
                videoService.deleteFile(videoFromDb.getVideo());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        videoService.update(video);

        return ResponseEntity.ok(videoService.getOneById(videoFromDb.getId()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Video video) {
        if(video != null){
            videoService.delete(video);
        }
    }
}
