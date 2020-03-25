package com.voicetm.karaoke.service;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.domain.Video;
import com.voicetm.karaoke.dto.VideoDto;
import com.voicetm.karaoke.repo.VideoRepo;
import com.voicetm.karaoke.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {
    public final static String VIDEO_PATH = "videos";

    private final VideoRepo videoRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public VideoService(VideoRepo videoRepo) {
        this.videoRepo = videoRepo;
    }

    public Long countBySinger(Singer singer){
        return videoRepo.countBySinger(singer);
    }

    public List<VideoDto> getAll(){
        return videoRepo.getAll();
    }

    public List<VideoDto> getAllByActiveAndVideoIsNotNull(boolean active){
        return videoRepo.getAllByActiveAndVideoIsNotNull(active);
    }

    public List<VideoDto> getAllBySingerAndActiveAndVideoIsNotNull(Singer singer){
        return videoRepo.getAllBySingerAndActiveAndVideoIsNotNull(singer);
    }

    public List<VideoDto> getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(boolean active, int count){
        List<VideoDto> sortedVideoList = new ArrayList<>();
        List<VideoDto> videoDtoList = videoRepo.getTopByActiveAndVideoIsNotNullOrderByWatchedCounterDesc(active);

        for(int i = 0; i < count; i++){
            if(videoDtoList.size() > i){
                sortedVideoList.add(videoDtoList.get(i));
            }
        }

        return sortedVideoList;
    }

    public void save(Video video) {
        video.setAddedDateTime(LocalDateTime.now());
        videoRepo.save(video);
    }

    public void update(Video video) {
        videoRepo.save(video);
    }

    public void updateStatus(Video video) {
        videoRepo.save(video);
    }

    public VideoDto getOneById(Long id) {
        return videoRepo.getOneById(id);
    }

    public void delete(Video video) {
        videoRepo.delete(video);
    }

    public void deleteAllBySinger(Singer singer){
        videoRepo.deleteAllBySinger(singer);
    }

    public void saveFile(Video video, MultipartFile uploadFile, boolean isVideo) throws IOException {

        if (uploadFile != null && !uploadFile.isEmpty()) {
            String uploadedFile = FileUploadUtil.saveFile(uploadFile, uploadPath, VIDEO_PATH);

            if(isVideo){
                video.setVideo(uploadedFile);
            }else{
                video.setImage(uploadedFile);
            }
        }
    }

    public void deleteFile(String fileName){
        if(!fileName.isEmpty()){
            File deleteDir = new File(uploadPath + "/" + VIDEO_PATH + "/" + fileName);

            if(deleteDir.exists()){
                deleteDir.delete();
            }
        }
    }
}
