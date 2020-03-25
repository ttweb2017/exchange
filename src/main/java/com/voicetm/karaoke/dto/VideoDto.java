package com.voicetm.karaoke.dto;

import com.voicetm.karaoke.domain.Singer;
import com.voicetm.karaoke.domain.Video;

public class VideoDto {
    private Long id;
    private String name;
    private String video;
    private String image;
    private int watchedCounter;
    private String addedDateTime;
    private boolean active;
    private SingerDto singer;

    public VideoDto() {
    }

    public VideoDto(Video video, Singer singer) {
        this.id = video.getId();
        this.name = video.getName();
        this.video = video.getVideo();
        this.image = video.getImage();
        this.watchedCounter = video.getWatchedCounter();
        this.addedDateTime = video.getFormattedDate();
        this.active = video.isActive();
        this.singer = new SingerDto(singer);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWatchedCounter() {
        return watchedCounter;
    }

    public void setWatchedCounter(int watchedCounter) {
        this.watchedCounter = watchedCounter;
    }

    public String getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(String addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SingerDto getSinger() {
        return singer;
    }

    public void setSinger(SingerDto singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", watchedCounter=" + watchedCounter +
                ", addedDateTime='" + addedDateTime + '\'' +
                ", active=" + active +
                ", singer=" + singer +
                '}';
    }
}
