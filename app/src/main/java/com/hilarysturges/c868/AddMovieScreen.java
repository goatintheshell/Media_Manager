package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.locks.ReentrantLock;

public class AddMovieScreen extends AppCompatActivity {

    private LinearLayout actorsLayout;
    private ImageButton addActorsButton;
    private final ReentrantLock lock = new ReentrantLock();
    private int lockCounter;

    static DBManager databaseMan;

    static final EditText[] addActors = new EditText[29];

    RadioGroup mediaType;
    RadioButton vhs;
    RadioButton dvd;
    RadioButton bluray;
    EditText titleEdit;
    EditText directorEdit;
    EditText lengthEdit;
    EditText ratingEdit;
    EditText descriptionEdit;
    Button addMovieButton;

    Bitmap blurayBMP;
    Bitmap dvdBMP;
    Bitmap vhsBMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_screen);

        databaseMan = new DBManager(getApplicationContext(), null, null, 1);

        actorsLayout = findViewById(R.id.addActorsMovieLayout);
        addActorsButton = findViewById(R.id.addActorsMovieButton);

        mediaType = findViewById(R.id.movieRadioGroup);
        vhs = findViewById(R.id.movieVHS);
        dvd = findViewById(R.id.movieDVD);
        bluray = findViewById(R.id.movieBluRay);
        titleEdit = findViewById(R.id.movieTitle);
        directorEdit = findViewById(R.id.movieDirector);
        lengthEdit = findViewById(R.id.movieLength);
        ratingEdit = findViewById(R.id.movieRating);
        descriptionEdit = findViewById(R.id.movieDesc);
        addMovieButton = findViewById(R.id.addMovieButton);

        blurayBMP = BitmapFactory.decodeResource(getResources(), R.drawable.bluray);
        dvdBMP = BitmapFactory.decodeResource(getResources(), R.drawable.dvd);
        vhsBMP = BitmapFactory.decodeResource(getResources(), R.drawable.vhs);

        lock.lock();

        addActorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lock.isLocked()) {
                    lock.unlock();
                } addActorEditText();
            }
        });

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!bluray.isChecked()) && (!dvd.isChecked()) && (!vhs.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (titleEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (directorEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (lengthEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (ratingEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (descriptionEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseMan.incrementSequence(titleEdit.getText().toString());
                    addMovie();
                    addActors();
                    Intent i = new Intent(getApplicationContext(), MoviesScreen.class);
                    startActivity(i);
                }
            }
        });
    }

    public void addMovie() {
        String title = titleEdit.getText().toString();
        String director = directorEdit.getText().toString();
        int length = Integer.parseInt(lengthEdit.getText().toString());
        String rating = ratingEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        int type = getType();
        Bitmap cover = getCover(type);
        Movie movie = new Movie(title, director, type, description, cover, length, rating);
        int sequenceId = databaseMan.getLastSequence();
        databaseMan.addMovie(movie, getNumActors(), sequenceId);
        Movie movie1 = databaseMan.getLastMovie();
        databaseMan.addDateMovie(movie1.get_id());
        MainActivity.movies.add(movie1);
        movie1.setAdded();
        movie1.setIndex();
    }

    public Bitmap getCover(int type) {
        if (type==1)
            return dvdBMP;
        if (type==2)
            return blurayBMP;
        else
            return vhsBMP;
    }

    public int getType() {
        int type = 0;
        if (dvd.isChecked())
            type++;
        if (bluray.isChecked())
            type=2;
        return type;
    }

    public int getNumActors() {
        int numActors = 0;
        for (int i=0 ; i<29 ; i++) {
            if (addActors[i]!=null)
                numActors++;
        }
        return numActors;
    }

    public void addActors() {
        int _id = MainActivity.movies.get(MainActivity.movies.size()-1).getSeqId();
        for (int i=0 ; i<29 ; i++) {
            if (addActors[i]!=null)
                if (!addActors[i].getText().toString().isEmpty()) {
                    String actorName = addActors[i].getText().toString();
                    databaseMan.addActor(actorName, _id);
                    Actor actor = databaseMan.getLastActor();
                    MainActivity.actors.add(actor);
                    actor.setIndex();
                    actor.setAdded();
                    actor.setMovie(databaseMan.getLastMovie());
                }
        }
    }

    public void addActorEditText() {
        while ((!lock.isLocked()) && addActors[28] == null) {
            addActors[lockCounter] = new EditText(getApplicationContext());
            addActors[lockCounter].setHint("Actor Name");
            actorsLayout.addView(addActors[lockCounter]);
            lockCounter++;
            lock.lock();
        }
    }
}
