package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private EditText searchField;
    private Button searchButton;

    static DBManager databaseMan;

    static ArrayList<ArrayList> searchResult = new ArrayList<>();

    static ArrayList<Music> music = new ArrayList<>();
    static ArrayList<Movie> movies = new ArrayList<>();
    static ArrayList<TVShow> tvShows = new ArrayList<>();
    static ArrayList<Track> tracks = new ArrayList<>();
    static ArrayList<Actor> actors = new ArrayList<>();
    static ArrayList<Season> seasons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(getApplicationContext(), null, null, 1);
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

            searchField = findViewById(R.id.searchField);
            searchButton = findViewById(R.id.searchButton);

            musicText = findViewById(R.id.musicMain);
            moviesText = findViewById(R.id.moviesMain);
            tvShowsText = findViewById(R.id.tvShowsMain);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String searchText = searchField.getText().toString();
                    ArrayList<Music> musicResult = searchMusic(searchText);
                    ArrayList<Movie> movieResult = searchMovies(searchText);
                    ArrayList<TVShow> tvShowResult = searchTVShows(searchText);
                    ArrayList<Track> trackResult = searchTracks(searchText);
                    ArrayList<Actor> actorResult = searchActors(searchText);
                    ArrayList<Season> seasonResult = searchSeasons(searchText);
                    if (!musicResult.isEmpty())
                        searchResult.add(musicResult);
                    if (!movieResult.isEmpty())
                        searchResult.add(movieResult);
                    if (!tvShowResult.isEmpty())
                        searchResult.add(tvShowResult);
                    if (!trackResult.isEmpty())
                        searchResult.add(trackResult);
                    if (!actorResult.isEmpty())
                        searchResult.add(actorResult);
                    if (!seasonResult.isEmpty())
                        searchResult.add(seasonResult);
                    Intent i = new Intent(getApplicationContext(), SearchResultScreen.class);
                    startActivity(i);
                }
            });

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

    public static void initData() {
        if (counterMusic == 0) {
            ArrayList<Music> dBMusic = databaseMan.musicToArray();
            for (int i=0 ; i<dBMusic.size() ; i++) {
                Music object = dBMusic.get(i);
                music.add(object);
                object.setIndex();
            }
            counterMusic++;
        }
        if (counterMovies == 0) {
            ArrayList<Movie> dBMovies = databaseMan.moviesToArray();
            for (int i=0 ; i<dBMovies.size() ; i++) {
                Movie object = dBMovies.get(i);
                movies.add(object);
                object.setIndex();
            }
            counterMovies++;
        }
        if (counterTVShows == 0) {
            ArrayList<TVShow> dBTVShows = databaseMan.tvShowsToArray();
            for (int i=0 ; i<dBTVShows.size() ; i++) {
                TVShow object = dBTVShows.get(i);
                tvShows.add(object);
                object.setIndex();
            }
            counterTVShows++;
        }
        if (counterTracks == 0) {
            ArrayList<Track> dBTracks = databaseMan.tracksToArray();
            for (int i=0 ; i<dBTracks.size() ; i++) {
                Track object = dBTracks.get(i);
                tracks.add(object);
                object.setIndex();
            }
            counterTracks++;
        }
        if (counterActors == 0) {
            ArrayList<Actor> dBActors = databaseMan.actorsToArray();
            for (int i=0 ; i<dBActors.size() ; i++) {
                Actor object = dBActors.get(i);
                actors.add(object);
                object.setIndex();
            }
            counterActors++;
        }
        if (counterSeasons == 0) {
            ArrayList<Season> dBSeasons = databaseMan.seasonsToArray();
            for (int i=0 ; i<dBSeasons.size() ; i++) {
                Season object = dBSeasons.get(i);
                seasons.add(object);
                object.setIndex();
            }
            counterSeasons++;
        }
    }

    public ArrayList<Music> searchMusic(String searchText) {
        ArrayList<Music> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.music.isEmpty()) {
            limit = MainActivity.music.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.music.get(i).getTitle());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                Music music = MainActivity.music.get(keys.get(i));

                searchResult.add(music);
            }
        }
        return searchResult;
    }

    public ArrayList<Movie> searchMovies(String searchText) {
        ArrayList<Movie> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.movies.isEmpty()) {
            limit = MainActivity.movies.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.movies.get(i).getTitle());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                Movie movie = MainActivity.movies.get(keys.get(i));
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public ArrayList<TVShow> searchTVShows(String searchText) {
        ArrayList<TVShow> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.tvShows.isEmpty()) {
            limit = MainActivity.tvShows.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.tvShows.get(i).getTitle());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                TVShow tvShow = MainActivity.tvShows.get(keys.get(i));
                searchResult.add(tvShow);
            }
        }
        return searchResult;
    }

    public ArrayList<Track> searchTracks(String searchText) {
        ArrayList<Track> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.tracks.isEmpty()) {
            limit = MainActivity.tracks.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.tracks.get(i).getTitle());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                Track track = MainActivity.tracks.get(keys.get(i));
                searchResult.add(track);
            }
        }
        return searchResult;
    }

    public ArrayList<Actor> searchActors(String searchText) {
        ArrayList<Actor> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.actors.isEmpty()) {
            limit = MainActivity.actors.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.actors.get(i).getName());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                Actor actor = MainActivity.actors.get(keys.get(i));
                searchResult.add(actor);
            }
        }
        return searchResult;
    }

    public ArrayList<Season> searchSeasons(String searchText) {
        ArrayList<Season> searchResult = new ArrayList<>();
        HashMap<Integer, String> titles = new HashMap<>();
        int limit;
        if (!MainActivity.seasons.isEmpty()) {
            limit = MainActivity.seasons.size();
            for (int i = 0; i < limit; i++) {
                titles.put(i, MainActivity.seasons.get(i).getTitle());
            }
            ArrayList<Integer> keys = getResultFromHashMap(titles, searchText);
            for (int i = 0; i < keys.size(); i++) {
                Season season = MainActivity.seasons.get(keys.get(i));
                searchResult.add(season);
            }
        }
        return searchResult;
    }

    public ArrayList<Integer> getResultFromHashMap(HashMap<Integer, String> hashMap, String searchText) {
        ArrayList<Integer> keys = new ArrayList<>();
        if (hashMap.containsValue(searchText)) {
            for (Map.Entry entry : hashMap.entrySet()) {
                if (Objects.equals(searchText, entry.getValue())) {
                    keys.add(Integer.parseInt(entry.getKey().toString()));
                }
            }
        }
        return keys;
    }
}

