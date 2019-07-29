package com.hilarysturges.c868;

import java.sql.Date;
import java.util.Calendar;

public class Season extends Media{
    private String title;
    private int tv_id;
    private TVShow tvShow;

    private Calendar cal = Calendar.getInstance();
    private Date curDate = new Date(cal.getTime().getTime());

    public Season(int _id, String title, int tv_id) {
        super(_id);
        this.title = title;
        this.tv_id = tv_id;
    }

    public Season(String title) {
        this.title = title;
    }

    public TVShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
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
