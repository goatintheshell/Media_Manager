package com.hilarysturges.c868;

import android.graphics.Bitmap;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class TVShow extends Media {

    private ArrayList<String> seasons;
    private int index;
    private Date added;

    private Calendar cal = Calendar.getInstance();
    private Date curDate = new Date(cal.getTime().getTime());

    public TVShow(String title, String director, int type, String description, Bitmap cover) {
        super(title, director, type, description, cover);
    }

    public TVShow(int _id, String title, String director, int type, String description, Bitmap cover, int seqId) {
        super(_id, title, director, type, description, cover, seqId);
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
        this.index = MainActivity.tvShows.lastIndexOf(this);
    }

    public ArrayList<String> getSeasons() {
        return seasons;
    }

    public String getSeason (int i) {
        return seasons.get(i);
    }

    public void setSeasons(ArrayList<String> seasons) {
        this.seasons = seasons;
    }

    public void setSeason(String season) {
        seasons.add(season);
    }
}
