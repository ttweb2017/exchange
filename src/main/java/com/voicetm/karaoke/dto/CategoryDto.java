package com.voicetm.karaoke.dto;

import com.voicetm.karaoke.domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private Long id;
    private String name;
    private int songCount;
    private boolean active;
    private List<SongDto> songs;

    public CategoryDto() {
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.active = category.isActive();
        this.songCount = category.getSongs().size();
        songs = new ArrayList<>();
        category.getSongs().forEach(song -> songs.add(new SongDto(song)));
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

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", songCount=" + songCount +
                ", active=" + active +
                ", songs=" + songs +
                '}';
    }
}
