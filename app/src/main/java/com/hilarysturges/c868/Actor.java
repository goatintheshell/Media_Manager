package com.hilarysturges.c868;

public class Actor extends Media{
    private String name;
    private int media_id;
    private Movie movie;
    private TVShow tvShow;

    public Actor(int _id, String name, int media_id) {
        super(_id);
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

    public Actor(String name) {
        this.name = name;
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
