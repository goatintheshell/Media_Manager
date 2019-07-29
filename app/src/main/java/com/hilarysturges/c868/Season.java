package com.hilarysturges.c868;

import java.sql.Date;
import java.util.Calendar;

public class Season {
    private int _id;
    private String title;
    private int tv_id;
    private int index;
    private Date added;
    private TVShow tvShow;

    private Calendar cal = Calendar.getInstance();
    private Date curDate = new Date(cal.getTime().getTime());

    public Season(int _id, String title, int tv_id) {
        this._id = _id;
        this.title = title;
        this.tv_id = tv_id;
    }

    public TVShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded() {
        this.added = curDate;
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
