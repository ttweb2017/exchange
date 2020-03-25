package com.voicetm.karaoke.service;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.domain.Song;
import com.voicetm.karaoke.dto.SongDto;
import com.voicetm.karaoke.repo.SongRepo;
import com.voicetm.karaoke.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    public final static String SONG_PATH = "songs";

    private final SongRepo songRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public SongService(SongRepo songRepo){
        this.songRepo = songRepo;
    }

    public List<SongDto> getAll() {
        return songRepo.getAll();
    }

    public void save(Song song) {
        song.setAddedDateTime(LocalDateTime.now());
        songRepo.save(song);
    }

    public SongDto getOneById(Long id) {
        return songRepo.getOneById(id);
    }

    public void update(Song song) {
        songRepo.save(song);
    }

    public void delete(Song song) {
        songRepo.delete(song);
        deleteFile(song.getImage());
        deleteFile(song.getVideo());
    }

    public void saveFile(Song song, MultipartFile uploadImage, boolean isVideo) throws IOException {
        if (uploadImage != null && !uploadImage.isEmpty()) {
            String uploadedFile = FileUploadUtil.saveFile(uploadImage, uploadPath, SONG_PATH);

            if (isVideo) song.setVideo(uploadedFile);
            else song.setImage(uploadedFile);
        }
    }

    public void deleteFile(String fileName) {
        FileUploadUtil.deleteFile(uploadPath + "/" + SONG_PATH + "/" + fileName);
    }

    public List<SongDto> getAllByCategoryAndActiveAndVideoIsNotNull(Category category, boolean active) {
        return songRepo.getAllByCategoryAndActiveAndVideoIsNotNull(category, active);
    }

    public List<SongDto> getAllByActiveAndVideoIsNotNull(boolean active) {
        return songRepo.getAllByActiveAndVideoIsNotNull(active);
    }

    public List<SongDto> getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(boolean active, int top) {
        List<SongDto> sortedSongList = new ArrayList<>();
        List<SongDto> songDtoList = songRepo.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(active);

        for(int i = 0; i < top; i++){
            if(songDtoList.size() > i){
                sortedSongList.add(songDtoList.get(i));
            }
        }

        return sortedSongList;
    }
}
