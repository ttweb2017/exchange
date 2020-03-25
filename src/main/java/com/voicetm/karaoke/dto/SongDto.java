package com.voicetm.karaoke.dto;

import com.voicetm.karaoke.domain.Category;
import com.voicetm.karaoke.domain.Song;

public class SongDto {
    private Long id;
    private String name;
    private String video;
    private String image;
    private int watchedCounter;
    private String addedDateTime;
    private boolean active;
    private CategoryDto category;

    public SongDto() {
    }

    public SongDto(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.video = song.getVideo();
        this.image = song.getImage();
        this.watchedCounter = song.getWatchedCounter();
        this.addedDateTime = song.getFormattedDate();
        this.active = song.isActive();
    }

    public SongDto(Song song, Category category) {
        this.id = song.getId();
        this.name = song.getName();
        this.video = song.getVideo();
        this.image = song.getImage();
        this.watchedCounter = song.getWatchedCounter();
        this.addedDateTime = song.getFormattedDate();
        this.active = song.isActive();
        this.category = new CategoryDto(category);
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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SongDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", watchedCounter=" + watchedCounter +
                ", addedDateTime='" + addedDateTime + '\'' +
                ", active=" + active +
                ", category=" + category +
                '}';
    }
}
