package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class DetailedMovieScreen extends AppCompatActivity {

    private DBManager databaseMan;

    private LinearLayout actorsLayout;
    private LinearLayout addActorsLayout;
    private ImageButton addActorsButton;
    private final ReentrantLock lock = new ReentrantLock();
    private int lockCounter;

    static final EditText[] addActors = new EditText[29];

    private EditText titleEdit;
    private EditText directorEdit;
    private EditText lengthEdit;
    private EditText ratingEdit;
    private EditText descEdit;

    private ArrayList<Actor> actors;
    private ArrayList<Actor> filteredActors;
    private ArrayList<Integer> actor_indexes = new ArrayList<>();
    private ArrayList<Integer> actor_ids = new ArrayList<>();

    private ArrayList<EditText> actorTexts = new ArrayList<>();

    private Button editMovieButton;
    private Button deleteMovieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie_screen);

        databaseMan = new DBManager(this, null, null, 1);

        Intent data = getIntent();
        final int index = data.getIntExtra("index",-1);
        final int _id = data.getIntExtra("ID", -1);

        actors = MainActivity.actors;

        actorsLayout = findViewById(R.id.movieActorsLayout);
        addActorsLayout = findViewById(R.id.addActorsMovieLayoutEditScreen);
        addActorsButton = findViewById(R.id.addActorsButtonMovieEditScreen);

        titleEdit = findViewById(R.id.movieTitleEdit);
        directorEdit = findViewById(R.id.movieDirectorEdit);
        lengthEdit = findViewById(R.id.movieLengthEdit);
        ratingEdit = findViewById(R.id.movieRatingEdit);
        descEdit = findViewById(R.id.movieDescEdit);

        editMovieButton = findViewById(R.id.editMovieButton);
        deleteMovieButton = findViewById(R.id.deleteMovieButton);

        Movie movie = MainActivity.movies.get(index);
        titleEdit.setText(movie.getTitle());
        directorEdit.setText(movie.getDirector());
        lengthEdit.setText(String.valueOf(movie.getLength()));
        ratingEdit.setText(movie.getRating());
        descEdit.setText(movie.getDescription());

        setActors(_id);
        lock.lock();

        addActorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lock.isLocked()) {
                    lock.unlock();
                } addActorEditText();
            }
        });

        deleteMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteActors(_id);
                deleteMovie(_id, index);
                Intent i = new Intent(getApplicationContext(), MoviesScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
            }
        });

        editMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMovie(_id, index);
                editActors(actor_ids, actor_indexes);
                addActors(_id);
                Intent i = new Intent(getApplicationContext(), MoviesScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Movie updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addActors(int _id) { ;
        for (int i=0 ; i<29 ; i++) {
            if (addActors[i]!=null)
                if (!addActors[i].getText().toString().isEmpty()) {
                    String actorName = addActors[i].getText().toString();
                    databaseMan.addActor(actorName, _id);
                    Actor actor = databaseMan.getLastActor();
                    MainActivity.actors.add(actor);
                }
        }
    }

    public void addActorEditText() {
        while ((!lock.isLocked()) && addActors[28] == null) {
            addActors[lockCounter] = new EditText(getApplicationContext());
            addActors[lockCounter].setHint("Actor Name");
            addActorsLayout.addView(addActors[lockCounter]);
            lockCounter++;
            lock.lock();
        }
    }

    public void editActors(ArrayList<Integer> actor_id, ArrayList<Integer> actor_index) {
        for (int i=0 ; i<filteredActors.size() ; i++) {
            String actorName = actorTexts.get(i).getText().toString();
            databaseMan.editActor(actor_id.get(i), actorName);
            MainActivity.actors.get(actor_index.get(i)).setName(actorName);
        }
    }

    public void editMovie(int _id, int index) {
        String title = titleEdit.getText().toString();
        String director = directorEdit.getText().toString();
        int length = Integer.parseInt(lengthEdit.getText().toString());
        String rating = ratingEdit.getText().toString();
        String description = descEdit.getText().toString();
        databaseMan.updateMovie(_id, title, director, length, rating, description);
        Movie movie = MainActivity.movies.get(index);
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setLength(length);
            movie.setRating(rating);
            movie.setDescription(description);
    }

    public void deleteActors(int _id) {
        databaseMan.removeActors(_id);
        MainActivity.actors.clear();
        MainActivity.counterActors = 0;
        MainActivity.initData();
    }

    public void deleteMovie(int _id, int index) {
        databaseMan.removeMovie(_id);
        MainActivity.movies.remove(index);
    }

    public void setActors(int _id) {
        filteredActors = new ArrayList<>();
        for (int i=0 ; i<actors.size() ; i++) {
            if (actors.get(i).getMedia_id() == _id) {
                Actor actor = new Actor(actors.get(i).get_id(), actors.get(i).getName(), actors.get(i).getMedia_id());
                filteredActors.add(actor);
                actor_indexes.add(i);
                actor_ids.add(actors.get(i).get_id());
            }
        }
        for (int i = 0 ; i<filteredActors.size() ; i++) {
            EditText actorText = new EditText(this);
            actorText.setText(filteredActors.get(i).getName());
            actorText.setTextSize(18);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100, 0, 40, 0);
            actorText.setLayoutParams(params);
            actorText.setId(i);
            actorTexts.add(actorText);
            actorsLayout.addView(actorTexts.get(i));
        }

    }
}
