package com.example.musiclisting;

public class AudioModel {

    long id;

    String title;

    int duration;

    int size;

    String artist;

    public AudioModel(long id, String title, int duration, int size, String artist) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.size = size;
        this.artist = artist;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
