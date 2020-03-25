package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Song;
import com.voicetm.karaoke.dto.SongDto;
import com.voicetm.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/song")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
public class AdminSongRestController {
    private final SongService songService;

    @Autowired
    public AdminSongRestController(SongService songService){
        this.songService = songService;
    }

    @GetMapping
    public List<SongDto> videoList(){
        return songService.getAll();
    }

    @PutMapping("/set-status/{song}/{status}")
    public ResponseEntity<?> updateSingerStatus(@PathVariable Song song, @PathVariable boolean status){
        if(song == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        song.setActive(status);
        songService.update(song);

        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<?> addSong(
            @ModelAttribute Song song,
            @RequestParam("imageFile") MultipartFile uploadImage,
            @RequestParam("videoFile") MultipartFile uploadVideo) {

        try {
            songService.saveFile(song, uploadImage, false);
            songService.saveFile(song, uploadVideo, true);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        songService.save(song);

        return ResponseEntity.ok(songService.getOneById(song.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Song songFromDb,
            @ModelAttribute Song song,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile
    ) {
        if(songFromDb == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        if(song.getName().isEmpty()){
            song.setName(songFromDb.getName());
        }

        if(song.getCategory() == null){
            song.setCategory(songFromDb.getCategory());
        }

        if(imageFile == null || imageFile.isEmpty()){
            song.setImage(songFromDb.getImage());
        }

        if(videoFile == null || videoFile.isEmpty()){
            song.setVideo(songFromDb.getVideo());
        }

        song.setActive(songFromDb.isActive());
        song.setAddedDateTime(songFromDb.getAddedDateTime());

        try {
            songService.saveFile(song, imageFile, false);
            songService.saveFile(song, videoFile, true);

            if(imageFile != null && !imageFile.isEmpty()){
                songService.deleteFile(songFromDb.getImage());
            }

            if(videoFile != null && !videoFile.isEmpty()){
                songService.deleteFile(songFromDb.getVideo());
            }

            songService.update(song);

            return ResponseEntity.ok(songService.getOneById(songFromDb.getId()));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Song song) {
        if(song != null){
            songService.delete(song);
        }
    }
}
