package com.voicetm.karaoke.service;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.dto.SingerDto;
import com.voicetm.karaoke.repo.SingerRepo;
import com.voicetm.karaoke.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class SingerService {
    public final static String SINGER_PATH = "singer";

    @Value("${upload.path}")
    private String uploadPath;

    private final SingerRepo singerRepo;
    private final VideoRepo videoRepo;
    // private final BiConsumer<EventType, Singer> wsSender;

    @Autowired
    public SingerService(SingerRepo singerRepo, VideoRepo videoRepo) {
        this.singerRepo = singerRepo;
        this.videoRepo = videoRepo;
        // this.wsSender = wsSender.getSender(ObjectType.SINGER, Views.FullMessage.class);

    }

    public List<Singer> findAll(){
        return singerRepo.findAll();
    }

    public List<Singer> findAllByActive(boolean active){
        return singerRepo.findAllByActive(active);
    }

    public List<Singer> findAllByActiveOrderByFirstNameAsc(boolean active){
        return singerRepo.findAllByActiveOrderByFirstNameAsc(active);
    }

    public List<SingerDto> getAllByActiveOrderByFirstNameAsc(boolean active){
        return singerRepo.getAllByActiveOrderByFirstNameAsc(active);
    }

    public List<SingerDto> getAll(){
        return singerRepo.getAll();
    }

    public List<SingerDto> getAllByActiveOrderByFirstName(boolean active){
        return singerRepo.getAllByActiveOrderByFirstNameAsc(active);
    }

    public Singer save(Singer singer) {
        if (singer.getAvatar().isEmpty()) {
            singer.setAvatar("default.svg");
        }

        singerRepo.save(singer);
        return singer;
    }

    public void updateStatus(Singer singer) {
        singerRepo.save(singer);
    }

    public Singer create(Singer singer) throws IOException {
        Singer updatedSinger = singerRepo.save(singer);
        // wsSender.accept(EventType.CREATE, updatedSinger);

        return updatedSinger;
    }

    @Transactional
    public void delete(Singer singer){
        deleteFile(singer.getAvatar());
        videoRepo.deleteAllBySinger(singer);
        singerRepo.delete(singer);
    }

    public void saveFile(Singer singer, MultipartFile uploadFile) throws IOException {
        if (uploadFile != null && !uploadFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            File singerDir = new File(uploadDir + "/" + SINGER_PATH);

            if(!singerDir.exists()){
                singerDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + uploadFile.getOriginalFilename();

            uploadFile.transferTo(new File(uploadPath + "/" + SINGER_PATH + "/" + resultFilename));

            singer.setAvatar(resultFilename);
        }
    }

    public void deleteFile(String fileName){
        if(!fileName.isEmpty()){
            File deleteDir = new File(uploadPath + "/" + SINGER_PATH + "/" + fileName);

            if(deleteDir.exists()){
                deleteDir.delete();
            }
        }
    }

    public Singer update(Singer singerFromDb) {
        return singerRepo.save(singerFromDb);
    }

    public SingerDto getOneById(Long id) {
        return singerRepo.getOneById(id);
    }

    /*public void delete(Singer singer) {
        singerRepo.delete(singer);
        wsSender.accept(EventType.REMOVE, singer);
    }*/
}
