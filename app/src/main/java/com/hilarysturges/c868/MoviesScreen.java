package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoviesScreen extends AppCompatActivity {

    LinearLayout movieHolderLayout;
    Button addNewMovie;
    Button returnHomeButton;

    TextView browseDirector;
    TextView browseActor;
    TextView browseABC;

    ArrayList<Movie> abcMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_screen);

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

            }
        });

        browseActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void alphabetizeMovies() {
        Collections.sort(abcMovies, new movieCompare());
    }

    public void clearMovies() {
        movieHolderLayout.removeAllViews();
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

            movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                    i.putExtra("index", movieTitle.getId());
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
            abcMovies.add(MainActivity.movies.get(i));

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
