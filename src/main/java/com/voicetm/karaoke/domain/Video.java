package com.voicetm.karaoke.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "singer_id")
    private Singer singer;

    private String video;

    private String image;

    private int watchedCounter = 0;

    private LocalDateTime addedDateTime;

    private boolean active;

    public String getFormattedDate(){

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        return addedDateTime.format(dateFormat);
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

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public LocalDateTime getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singer=" + singer +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", watchedCounter=" + watchedCounter +
                ", addedDateTime=" + addedDateTime +
                ", active=" + active +
                '}';
    }
}

