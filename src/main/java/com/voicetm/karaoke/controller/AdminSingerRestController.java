package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.dto.SingerDto;
import com.voicetm.karaoke.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/singer")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
public class AdminSingerRestController {
    private final SingerService singerService;

    @Autowired
    public AdminSingerRestController(SingerService singerService){
        this.singerService = singerService;
    }

    @GetMapping
    public List<SingerDto> singerList(){
        return singerService.getAll();
    }

    @PutMapping("/set-status/{singer}/{status}")
    public ResponseEntity<?> updateSingerStatus(@PathVariable Singer singer, @PathVariable boolean status){
        if(singer == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        singer.setActive(status);
        singerService.updateStatus(singer);

        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<?> addSinger(
            @ModelAttribute Singer singer,
            @RequestParam("file") MultipartFile uploadFile) {

        try {
            singerService.saveFile(singer, uploadFile);
            singerService.save(singer);

            return ResponseEntity.ok(singerService.getOneById(singer.getId()));
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Singer singerFromDb,
            @ModelAttribute Singer singer,
            @RequestParam(value = "file", required = false) MultipartFile uploadFile
    ) {
        if(singerFromDb == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        if(singer.getFirstName().isEmpty()){
            singer.setFirstName(singerFromDb.getFirstName());
        }

        if(singer.getLastName().isEmpty()){
            singer.setLastName(singerFromDb.getLastName());
        }

        if(uploadFile == null || uploadFile.isEmpty()){
            singer.setAvatar(singerFromDb.getAvatar());
        }

        singer.setActive(singerFromDb.isActive());

        try {
            singerService.saveFile(singer, uploadFile);
            singerService.deleteFile(singerFromDb.getAvatar());
        } catch (IOException e) {
            e.printStackTrace();
        }

        singerService.update(singer);

        return ResponseEntity.ok(singerService.getOneById(singerFromDb.getId()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Singer singer) {
        if(singer != null){
            singerService.delete(singer);
        }
    }
}
