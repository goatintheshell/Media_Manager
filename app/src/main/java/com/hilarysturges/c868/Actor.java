package com.hilarysturges.c868;


import java.sql.Date;
import java.util.Calendar;

public class Actor {
    private int _id;
    private String name;
    private int media_id;
    private int index;
    private Date added;
    private Movie movie;
    private TVShow tvShow;

    private Calendar cal = Calendar.getInstance();
    private Date curDate = new Date(cal.getTime().getTime());

    public Actor(int _id, String name, int media_id) {
        this._id = _id;
        this.name = name;
        this.media_id = media_id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
        this.index = MainActivity.actors.lastIndexOf(this);
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
