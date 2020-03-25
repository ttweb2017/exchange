package com.voicetm.karaoke.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private Set<Video> songs;

    private String firstName;

    private String lastName;

    @Transient
    private String fullName;

    @Transient
    private int songCount;

    private String avatar;

    private boolean active;

    public String getFullName(){
        fullName = firstName + " " + lastName;
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSongCount() {
        songCount = songs.size();
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public Set<Video> getSongs() {
        return songs;
    }

    public void setSongs(Set<Video> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", songCount=" + songCount +
                ", avatar='" + avatar + '\'' +
                ", active=" + active +
                '}';
    }
}
