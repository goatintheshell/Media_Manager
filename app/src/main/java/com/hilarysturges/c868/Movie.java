package com.hilarysturges.c868;

import android.graphics.Bitmap;

public class Movie extends Video {
    private int length;
    private String rating;

    public Movie(String title, String director, int type, String description, Bitmap cover, int length, String rating) {
        super(title, director, type, description, cover);
        this.length = length;
        this.rating = rating;
    }

    public Movie(int _id, String title, String director, int type, String description, Bitmap cover, int length, String rating, int seqId) {
        super(_id, title, director, type, description, cover, seqId);
        this.length = length;
        this.rating = rating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
