package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static int counterMusic;
    static int counterMovies;
    static int counterTVShows;
    static int counterTracks;
    static int counterActors;
    static int counterSeasons;

    private TextView musicText;
    private TextView moviesText;
    private TextView tvShowsText;
    private Button goButton;

    static DBManager databaseMan;

    static ArrayList<Music> music = new ArrayList<>();
    static ArrayList<Movie> movies = new ArrayList<>();
    static ArrayList<TVShow> tvShows = new ArrayList<>();
    static ArrayList<Track> tracks = new ArrayList<>();
    static ArrayList<Actor> actors = new ArrayList<>();
    static ArrayList<Season> seasons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(getApplicationContext(),null,null,1 );
        initData();

        //if the user has no media, show activity_main
        if (music.isEmpty() && movies.isEmpty() && tvShows.isEmpty()) {
            setContentView(R.layout.activity_main);

            goButton = findViewById(R.id.goButton);

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), AddMediaScreen.class);
                    startActivity(i);
                }
            });

        } else {
            //if the user has media, show activity_main2
            setContentView(R.layout.activity_main2);

            musicText = findViewById(R.id.musicMain);
            moviesText = findViewById(R.id.moviesMain);
            tvShowsText = findViewById(R.id.tvShowsMain);

            musicText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MusicScreen.class);
                    startActivity(i);

                }
            });

            moviesText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MoviesScreen.class);
                    startActivity(i);

                }
            });

            tvShowsText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), TVShowsScreen.class);
                    startActivity(i);

                }
            });
        }
    }

    public void initData() {
        if (counterMusic == 0) {
            ArrayList<Music> dBMusic = databaseMan.musicToArray();
            music.addAll(dBMusic);
            counterMusic++;
        }
        if (counterMovies == 0) {
            ArrayList<Movie> dBMovies = databaseMan.moviesToArray();
            movies.addAll(dBMovies);
            counterMovies++;
        }
        if (counterTVShows == 0) {
            ArrayList<TVShow> dBTVShows = databaseMan.tvShowsToArray();
            tvShows.addAll(dBTVShows);
            counterTVShows++;
        }
        if (counterTracks == 0) {
            ArrayList<Track> dBTracks = databaseMan.tracksToArray();
            tracks.addAll(dBTracks);
            counterTracks++;
        }
        if (counterActors == 0) {
            ArrayList<Actor> dBActors = databaseMan.actorsToArray();
            actors.addAll(dBActors);
            counterActors++;
        }
        if (counterSeasons == 0) {
            ArrayList<Season> dBSeasons = databaseMan.seasonsToArray();
            seasons.addAll(dBSeasons);
            counterSeasons++;
        }
    }
}
