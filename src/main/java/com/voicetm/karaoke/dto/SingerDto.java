package com.voicetm.karaoke.dto;

import com.voicetm.karaoke.domain.Singer;

public class SingerDto {
    private Long id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String avatar;
    private int songCount;
    private boolean active;

    public SingerDto() {
    }

    public SingerDto(Singer singer) {
        this.id = singer.getId();
        this.fullName = singer.getFirstName() + " " + singer.getLastName();
        this.firstName = singer.getFirstName();
        this.lastName = singer.getLastName();
        this.avatar = singer.getAvatar();
        this.songCount = singer.getSongs().size();
        this.active = singer.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @Override
    public String toString() {
        return "SingerDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", songCount=" + songCount +
                ", active=" + active +
                '}';
    }
}
