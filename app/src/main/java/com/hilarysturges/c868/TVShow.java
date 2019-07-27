package com.hilarysturges.c868;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class TVShow extends Media {

    private ArrayList<String> seasons;
    private int index;

    public TVShow(String title, String director, int type, String description, Bitmap cover) {
        super(title, director, type, description, cover);
    }

    public TVShow(int _id, String title, String director, int type, String description, Bitmap cover, int seqId) {
        super(_id, title, director, type, description, cover, seqId);
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
