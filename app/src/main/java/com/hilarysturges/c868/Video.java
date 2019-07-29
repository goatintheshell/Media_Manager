package com.hilarysturges.c868;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class Video extends Media {
    private String title;
    private String director;
    private int type;
    private ArrayList<Actor> actors;
    private String description;
    private Bitmap cover;
    private int seqId;

    public Video(String title, String director, int type, String description, Bitmap cover) {
        this.title = title;
        this.director = director;
        this.type = type;
        this.description = description;
        this.cover = cover;
    }

    public Video(int _id, String title, String director, int type, String description, Bitmap cover, int seqId) {
        super(_id);
        this.title = title;
        this.director = director;
        this.type = type;
        this.description = description;
        this.cover = cover;
        this.seqId = seqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public Actor getActor(int i) {
        return actors.get(i);
    }

    public void setActors(ArrayList<Actor> actors) { this.actors = actors; }

    public void setActor(Actor actor) {
        actors.add(actor);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }
}
