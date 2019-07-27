package com.hilarysturges.c868;

public class Season {
    private int _id;
    private String title;
    private int tv_id;
    private int index;

    public Season(int _id, String title, int tv_id) {
        this._id = _id;
        this.title = title;
        this.tv_id = tv_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex() {
        this.index = MainActivity.seasons.lastIndexOf(this);
    }

    public Season(String title) {
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

    public int getTv_id() {
        return tv_id;
    }

    public void setTv_id(int tv_id) {
        this.tv_id = tv_id;
    }
}
