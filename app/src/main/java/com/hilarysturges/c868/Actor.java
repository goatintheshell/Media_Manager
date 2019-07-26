package com.hilarysturges.c868;

public class Actor {
    private int _id;
    private String name;
    private int media_id;

    public Actor(int _id, String name, int media_id) {
        this._id = _id;
        this.name = name;
        this.media_id = media_id;
    }

    public Actor(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }
}
