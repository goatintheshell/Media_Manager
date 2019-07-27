package com.hilarysturges.c868;

public class Track {
    private int _id;
    private String title;
    private int music_id;
    private int index;

    public Track(int _id, String title, int music_id) {
        this._id = _id;
        this.title = title;
        this.music_id = music_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex() {
        this.index = MainActivity.tracks.lastIndexOf(this);
    }

    public Track(String title) {
        this.title = title;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
