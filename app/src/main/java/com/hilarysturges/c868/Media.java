package com.hilarysturges.c868;

import java.sql.Date;
import java.util.Calendar;

public class Media {
    private int _id;
    private int index;
    private Date added;
    private Calendar cal = Calendar.getInstance();
    private Date curDate = new Date(cal.getTime().getTime());

    public Media() {
    }

    public Media(int id) {
        this._id = id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex() {
        if (this instanceof Music)
            this.index = MainActivity.music.lastIndexOf(this);
        if (this instanceof Movie)
            this.index = MainActivity.movies.lastIndexOf(this);
        if (this instanceof TVShow)
            this.index = MainActivity.tvShows.lastIndexOf(this);
        if (this instanceof Track)
            this.index = MainActivity.tracks.lastIndexOf(this);
        if (this instanceof Actor)
            this.index = MainActivity.actors.lastIndexOf(this);
        if (this instanceof Season)
            this.index = MainActivity.seasons.lastIndexOf(this);
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded() {
        this.added = curDate;
    }
}
