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

public class DetailedTVShowScreen extends AppCompatActivity {

    private DBManager databaseMan;

    private LinearLayout actorsLayout;
    private LinearLayout seasonsLayout;
    private LinearLayout addActorsLayout;
    private ImageButton addActorsButton;
    private LinearLayout addSeasonsLayout;
    private ImageButton addSeasonsButton;
    private final ReentrantLock lockActors = new ReentrantLock();
    private int lockCounterActors;
    private final ReentrantLock lockSeasons = new ReentrantLock();
    private int lockCounterSeasons;

    static final EditText[] addActors = new EditText[29];
    static final EditText[] addSeasons = new EditText[29];

    private EditText titleEdit;
    private EditText directorEdit;
    private EditText descEdit;

    private ArrayList<Actor> actors;
    private ArrayList<Actor> filteredActors;
    private ArrayList<Integer> actor_indexes = new ArrayList<>();
    private ArrayList<Integer> actor_ids = new ArrayList<>();

    private ArrayList<Season> seasons;
    private ArrayList<Season> filteredSeasons;
    private ArrayList<Integer> season_indexes = new ArrayList<>();
    private ArrayList<Integer> season_ids = new ArrayList<>();

    private ArrayList<EditText> actorTexts = new ArrayList<>();
    private ArrayList<EditText> seasonTexts = new ArrayList<>();

    private Button editTVShowButton;
    private Button deleteTVShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tvshow_screen);

        databaseMan = new DBManager(this, null, null, 1);

        Intent data = getIntent();
        final int index = data.getIntExtra("index",-1);
        final int _id = data.getIntExtra("ID", -1);

        actors = MainActivity.actors;
        seasons = MainActivity.seasons;

        actorsLayout = findViewById(R.id.tvShowActorsLayout);
        seasonsLayout = findViewById(R.id.tvShowSeasonsLayout);
        addActorsLayout = findViewById(R.id.addActorsTVShowLayoutEditScreen);
        addActorsButton = findViewById(R.id.addActorsButtonTVShowEditScreen);
        addSeasonsLayout = findViewById(R.id.addSeasonsLayoutEditScreen);
        addSeasonsButton = findViewById(R.id.addSeasonsButtonEditScreen);

        titleEdit = findViewById(R.id.tvShowTitleEdit);
        directorEdit = findViewById(R.id.tvShowDireEdit);
        descEdit = findViewById(R.id.tvShowDescEdit);

        editTVShowButton = findViewById(R.id.editTVShowButton);
        deleteTVShowButton = findViewById(R.id.deleteTVShowButton);

        TVShow tvShow = MainActivity.tvShows.get(index);
        titleEdit.setText(tvShow.getTitle());
        directorEdit.setText(tvShow.getDirector());
        descEdit.setText(tvShow.getDescription());

        setActors(_id);
        setSeasons(_id);
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

        deleteTVShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTVShow(_id, index);
                Intent i = new Intent(getApplicationContext(), TVShowsScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "TV Show deleted", Toast.LENGTH_SHORT).show();
            }
        });

        editTVShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTVShow(_id, index);
                editActors(actor_ids, actor_indexes);
                editSeasons(season_ids, season_indexes);
                addActors(_id);
                addSeasons(_id);
                Intent i = new Intent(getApplicationContext(), TVShowsScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "TV Show updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addActors(int _id) {
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

    public void addSeasons(int _id) {
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
            addActorsLayout.addView(addActors[lockCounterActors]);
            lockCounterActors++;
            lockActors.lock();
        }
    }

    public void addSeasonEditText() {
        while ((!lockSeasons.isLocked()) && addSeasons[28] == null) {
            addSeasons[lockCounterSeasons] = new EditText(getApplicationContext());
            addSeasons[lockCounterSeasons].setHint("Season Title");
            addSeasonsLayout.addView(addSeasons[lockCounterSeasons]);
            lockCounterSeasons++;
            lockSeasons.lock();
        }
    }

    public void editActors(ArrayList<Integer> actor_id, ArrayList<Integer> actor_index) {
        for (int i=0 ; i<filteredActors.size() ; i++) {
            String actorName = actorTexts.get(i).getText().toString();
            databaseMan.editActor(actor_id.get(i), actorName);
            MainActivity.actors.get(actor_index.get(i)).setName(actorName);
        }
    }

    public void editSeasons(ArrayList<Integer> season_id, ArrayList<Integer> season_index) {
        for (int i=0 ; i<filteredSeasons.size() ; i++) {
            String seasonTitle = seasonTexts.get(i).getText().toString();
            databaseMan.editSeason(season_id.get(i), seasonTitle);
            MainActivity.seasons.get(season_index.get(i)).setTitle(seasonTitle);
        }
    }

    public void editTVShow(int _id, int index) {
        String title = titleEdit.getText().toString();
        String director = directorEdit.getText().toString();
        String description = descEdit.getText().toString();
        databaseMan.updateTVShow(_id, title, director, description);
        TVShow tvShow = MainActivity.tvShows.get(index);
        tvShow.setTitle(title);
        tvShow.setDirector(director);
        tvShow.setDescription(description);
    }

    public void deleteTVShow(int _id, int index) {
        databaseMan.removeTVShow(_id);
        MainActivity.tvShows.remove(index);
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

    public void setSeasons(int _id) {
        filteredSeasons = new ArrayList<>();
        for (int i=0 ; i<seasons.size() ; i++) {
            if (seasons.get(i).getTv_id() == _id) {
                Season season = new Season(seasons.get(i).get_id(), seasons.get(i).getTitle(), seasons.get(i).getTv_id());
                filteredSeasons.add(season);
                season_indexes.add(i);
                season_ids.add(seasons.get(i).get_id());
            }
        }
        for (int i = 0 ; i<filteredSeasons.size() ; i++) {
            EditText seasonText = new EditText(this);
            seasonText.setText(filteredSeasons.get(i).getTitle());
            seasonText.setTextSize(18);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100, 0, 40, 0);
            seasonText.setLayoutParams(params);
            seasonText.setId(i);
            seasonTexts.add(seasonText);
            seasonsLayout.addView(seasonTexts.get(i));
        }

    }
}
