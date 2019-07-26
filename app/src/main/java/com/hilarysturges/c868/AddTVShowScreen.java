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

import java.util.concurrent.locks.ReentrantLock;

public class AddTVShowScreen extends AppCompatActivity {

    private LinearLayout actorsLayout;
    private ImageButton addActorsButton;
    private LinearLayout seasonsLayout;
    private ImageButton addSeasonsButton;
    private final ReentrantLock lockActors = new ReentrantLock();
    private int lockCounterActors;
    private final ReentrantLock lockSeasons = new ReentrantLock();
    private int lockCounterSeasons;

    static DBManager databaseMan;

    static final EditText[] addActors = new EditText[29];
    static final EditText[] addSeasons = new EditText[29];

    RadioButton dvd;
    RadioButton bluray;
    EditText titleEdit;
    EditText directorEdit;
    EditText descriptionEdit;
    Button addTVShowButton;

    Bitmap blurayBMP;
    Bitmap dvdBMP;
    Bitmap vhsBMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tvshow_screen);

        databaseMan = new DBManager(getApplicationContext(), null, null, 1);

        actorsLayout = findViewById(R.id.addActorsTVShowLayout);
        addActorsButton = findViewById(R.id.addActorsTVShowButton);
        seasonsLayout = findViewById(R.id.addSeasonsLayout);
        addSeasonsButton = findViewById(R.id.addSeasonsButton);

        dvd = findViewById(R.id.tvShowDVD);
        bluray = findViewById(R.id.tvShowBluRay);
        titleEdit = findViewById(R.id.tvShowTitle);
        directorEdit = findViewById(R.id.tvShowDire);
        descriptionEdit = findViewById(R.id.tvShowDesc);
        addTVShowButton = findViewById(R.id.addTVShowButton);

        blurayBMP = BitmapFactory.decodeResource(getResources(), R.drawable.bluray);
        dvdBMP = BitmapFactory.decodeResource(getResources(), R.drawable.dvd);
        vhsBMP = BitmapFactory.decodeResource(getResources(), R.drawable.vhs);

        lockActors.lock();
        lockSeasons.lock();

        addActorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lockActors.isLocked()) {
                    lockActors.unlock();
                } addActorEditText();
            }
        });

        addSeasonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lockSeasons.isLocked()) {
                    lockSeasons.unlock();
                } addSeasonEditText();
            }
        });

        addTVShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addTVShow();
                    addActors();
                    addSeasons();
                } catch (Exception e) {
                    System.out.println("Adding show failed: " + e.getMessage());
                }
                Intent i = new Intent(getApplicationContext(), TVShowsScreen.class);
                startActivity(i);
            }
        });

    }

    public void addTVShow() {
        String title = titleEdit.getText().toString();
        String director = directorEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        int type = getType();
        Bitmap cover = getCover(type);
        TVShow tvShow = new TVShow(title, director, type, description, cover);
        databaseMan.addTVShow(tvShow, getNumActors(), getNumSeasons());
        TVShow tvShow1 = databaseMan.getLastTVShow();
        MainActivity.tvShows.add(tvShow1);
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

    public int getNumSeasons() {
        int numSeasons = 0;
        for (int i=0 ; i<29 ; i++) {
            if (addSeasons[i]!=null)
                numSeasons++;
        }
        return numSeasons;
    }

    public void addActors() {
        int _id = MainActivity.tvShows.get(MainActivity.tvShows.size()-1).get_id();
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

    public void addSeasons() {
        int _id = MainActivity.tvShows.get(MainActivity.tvShows.size()-1).get_id();
        for (int i=0 ; i<29 ; i++) {
            if (addSeasons[i]!=null)
                if (!addSeasons[i].getText().toString().isEmpty()) {
                    String seasonTitle = addSeasons[i].getText().toString();
                    databaseMan.addSeason(seasonTitle, _id);
                    Season season = databaseMan.getLastSeason();
                    MainActivity.seasons.add(season);
                }
        }
    }

    public void addActorEditText() {
        while ((!lockActors.isLocked()) && addActors[28] == null) {
            addActors[lockCounterActors] = new EditText(getApplicationContext());
            addActors[lockCounterActors].setHint("Actor Name");
            actorsLayout.addView(addActors[lockCounterActors]);
            lockCounterActors++;
            lockActors.lock();
        }
    }

    public void addSeasonEditText() {
        while ((!lockSeasons.isLocked()) && addSeasons[28] == null) {
            addSeasons[lockCounterSeasons] = new EditText(getApplicationContext());
            addSeasons[lockCounterSeasons].setHint("Season Title");
            seasonsLayout.addView(addSeasons[lockCounterSeasons]);
            lockCounterSeasons++;
            lockSeasons.lock();
        }
    }
}