package com.hilarysturges.c868;

public class Track extends Media {
    private String title;
    private int music_id;
    private Music music;

    public Track(int _id, String title, int music_id) {
        super(_id);
        this.title = title;
        this.music_id = music_id;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Track(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }
}
