package com.hilarysturges.c868;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class Music extends Media {

    private String title;
    private String artist;
    private String producer;
    private int length;
    private ArrayList<Track> tracks;
    private Bitmap cover;
    private int type;
    private String description;


    public Music(String title, String artist, String producer, int length, Bitmap cover, int type, String description) {
        this.title = title;
        this.artist = artist;
        this.producer = producer;
        this.length = length;
        this.cover = cover;
        this.type = type;
        this.description = description;
    }

    public Music(int _id, String title, String artist, String producer, int length, Bitmap cover, int type, String description) {
        super(_id);
        this.title = title;
        this.artist = artist;
        this.producer = producer;
        this.length = length;
        this.cover = cover;
        this.type = type;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList getTracks() {
        return tracks;
    }

    public Track getTrack(int i) {
        return tracks.get(i);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setTrack(Track track) {
        tracks.add(track);
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
