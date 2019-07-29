package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MoviesScreen extends AppCompatActivity {

    DBManager databaseMan;

    LinearLayout movieHolderLayout;
    Button addNewMovie;
    Button returnHomeButton;

    TextView browseDirector;
    TextView browseActor;
    TextView browseABC;

    HashMap<Integer, Integer> indexAndId = new HashMap<>();

    ArrayList<Movie> abcMovies = new ArrayList<>();
    ArrayList<Movie> abcDirectors = new ArrayList<>();
    ArrayList<Actor> abcActors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_screen);

        databaseMan = new DBManager(this, null, null, 1);

        abcMovies.addAll(MainActivity.movies);
        abcDirectors.addAll(MainActivity.movies);
        abcActors = databaseMan.getMovieActors();

        movieHolderLayout = findViewById(R.id.movieHolderLayout);
        addNewMovie = findViewById(R.id.addNewMovie);
        returnHomeButton = findViewById(R.id.returnToHomeMovies);

        browseDirector = findViewById(R.id.browseByDirectorMovies);
        browseActor = findViewById(R.id.browseByActorMovies);
        browseABC = findViewById(R.id.browseAlphabeticallyMovies);

        addMovies();

        browseDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeDirectors();
                clearMovies();
                setAlphabetizedDirectors();
            }
        });

        browseActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeActors();
                clearMovies();
                setAlphabetizedActors();
            }
        });

        browseABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeMovies();
                clearMovies();
                setAlphabetizedMovies();
            }
        });

        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        addNewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMovieScreen.class);
                startActivity(i);
            }
        });
    }

    public void alphabetizeDirectors() {
        Collections.sort(abcDirectors, new directorCompare());
    }

    public void alphabetizeMovies() {
        Collections.sort(abcMovies, new movieCompare());
    }

    public void alphabetizeActors() {
        Collections.sort(abcActors, new actorCompare());
    }

    public void clearMovies() {
        movieHolderLayout.removeAllViews();
    }

    public void setAlphabetizedActors() {
        for (int i=0 ; i<abcActors.size() ; i++) {
            LinearLayout movieCellLayout = new LinearLayout(this);
            movieCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcActors.get(i).getMedia_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            Bitmap movieCover = MainActivity.movies.get(index[0]).getCover();
            final TextView movieTitle = new TextView(this);
            movieTitle.setText(abcActors.get(i).getName());
            movieTitle.setTextSize(30);
            movieTitle.setPadding(15,0,0,0);
            movieTitle.setId(i);
            ImageView movieImage = setMovieImageDetails(movieCover);
            movieCellLayout.addView(movieImage);
            movieCellLayout.addView(movieTitle);
            movieHolderLayout.addView(movieCellLayout);
            movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcActors.get(movieTitle.getId()).getMedia_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedDirectors() {
        for (int i=0 ; i<abcDirectors.size() ; i++) {
            LinearLayout movieCellLayout = new LinearLayout(this);
            movieCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap movieCover = abcDirectors.get(i).getCover();
            final TextView movieTitle = new TextView(this);
            movieTitle.setText(abcDirectors.get(i).getDirector());
            movieTitle.setTextSize(30);
            movieTitle.setPadding(15,0,0,0);
            movieTitle.setId(i);
            ImageView movieImage = setMovieImageDetails(movieCover);
            movieCellLayout.addView(movieImage);
            movieCellLayout.addView(movieTitle);
            movieHolderLayout.addView(movieCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcDirectors.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcDirectors.get(movieTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedMovies() {
        for (int i=0 ; i<abcMovies.size() ; i++) {
            LinearLayout movieCellLayout = new LinearLayout(this);
            movieCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap movieCover = abcMovies.get(i).getCover();
            final TextView movieTitle = new TextView(this);
            movieTitle.setText(abcMovies.get(i).getTitle());
            movieTitle.setTextSize(30);
            movieTitle.setPadding(15,0,0,0);
            movieTitle.setId(i);
            ImageView movieImage = setMovieImageDetails(movieCover);
            movieCellLayout.addView(movieImage);
            movieCellLayout.addView(movieTitle);
            movieHolderLayout.addView(movieCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcMovies.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                    }
                }
            movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcMovies.get(movieTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public void addMovies() {
        for (int i=0 ; i<MainActivity.movies.size() ; i++) {
            LinearLayout movieCellLayout = new LinearLayout(this);
                movieCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap movieCover = MainActivity.movies.get(i).getCover();
            final TextView movieTitle = new TextView(this);
                movieTitle.setText(MainActivity.movies.get(i).getTitle());
                movieTitle.setTextSize(30);
                movieTitle.setPadding(15,0,0,0);
                movieTitle.setId(i);
            ImageView movieImage = setMovieImageDetails(movieCover);
            movieCellLayout.addView(movieImage);
            movieCellLayout.addView(movieTitle);
            movieHolderLayout.addView(movieCellLayout);
            indexAndId.put(MainActivity.movies.get(i).get_id(), i);
            movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                    i.putExtra("index", movieTitle.getId());
                    i.putExtra("ID", MainActivity.movies.get(movieTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public ImageView setMovieImageDetails(Bitmap movieCover) {
        ImageView movieImage = new ImageView(this);
        movieImage.setImageBitmap(movieCover);
        movieImage.setMaxWidth(150);
        movieImage.setMaxHeight(150);
        movieImage.setAdjustViewBounds(true);
        movieImage.setPadding(30,0,0,10);
        return movieImage;
    }
}

class movieCompare implements Comparator<Movie> {
    @Override
    public int compare(Movie movie, Movie movie2) {
        if (movie.getTitle().compareTo(movie2.getTitle()) > 0) {
            return 1;
        } if (movie2.getTitle().compareTo(movie.getTitle()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class directorCompare implements Comparator<Movie> {
    @Override
    public int compare(Movie movie, Movie movie2) {
        if (movie.getDirector().compareTo(movie2.getDirector()) > 0) {
            return 1;
        } if (movie2.getDirector().compareTo(movie.getDirector()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class actorCompare implements Comparator<Actor> {
    @Override
    public int compare(Actor actor, Actor actor2) {
        if (actor.getName().compareTo(actor2.getName()) > 0) {
            return 1;
        } if (actor2.getName().compareTo(actor.getName()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
