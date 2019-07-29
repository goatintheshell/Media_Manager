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

public class TVShowsScreen extends AppCompatActivity {

    DBManager databaseMan;

    LinearLayout tvShowHolderLayout;
    Button addNewTVShowButton;
    Button returnHomeButton;

    TextView browseDirector;
    TextView browseActor;
    TextView browseSeason;
    TextView browseABC;

    HashMap<Integer, Integer> indexAndId = new HashMap<>();

    ArrayList<TVShow> abcTVShows = new ArrayList<>();
    ArrayList<TVShow> abcDirectors = new ArrayList<>();
    ArrayList<Actor> abcActors = new ArrayList<>();
    ArrayList<Season> abcSeasons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_screen);

        databaseMan = new DBManager(this, null, null, 1);

        abcTVShows.addAll(MainActivity.tvShows);
        abcDirectors.addAll(MainActivity.tvShows);
        abcActors = databaseMan.getTVShowActors();
        abcSeasons.addAll(MainActivity.seasons);

        tvShowHolderLayout = findViewById(R.id.tvShowHolderLayout);
        addNewTVShowButton = findViewById(R.id.addNewTVShowButton);
        returnHomeButton = findViewById(R.id.returnToHomeTVShows);

        browseDirector = findViewById(R.id.browseByDirectorTVShow);
        browseActor = findViewById(R.id.browseByActorTVShow);
        browseABC = findViewById(R.id.browseAlphabeticallyTVShow);
        browseSeason = findViewById(R.id.browseBySeasonTVShow);

        addTVShows();

        browseDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeDirectors();
                clearTVShows();
                setAlphabetizedDirectors();
            }
        });

        browseActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeActors();
                clearTVShows();
                setAlphabetizedActors();
            }
        });

        browseSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeSeasons();
                clearTVShows();
                setAlphabetizedSeasons();
            }
        });

        browseABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeTVShows();
                clearTVShows();
                setAlphabetizedTVShows();
            }
        });

        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        addNewTVShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTVShowScreen.class);
                startActivity(i);
            }
        });
    }

    public void alphabetizeDirectors() {
        Collections.sort(abcDirectors, new directorCompareTV());
    }

    public void alphabetizeTVShows() {
        Collections.sort(abcTVShows, new tvShowCompare());
    }

    public void alphabetizeSeasons() {
        Collections.sort(abcSeasons, new seasonCompare());
    }

    public void alphabetizeActors() {
        Collections.sort(abcActors, new actorCompare());
    }

    public void clearTVShows() {
        tvShowHolderLayout.removeAllViews();
    }

    public void setAlphabetizedSeasons() {
        for (int i=0 ; i<abcSeasons.size() ; i++) {
            LinearLayout tvShowCellLayout = new LinearLayout(this);
            tvShowCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcSeasons.get(i).getTv_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            Bitmap tvShowCover = MainActivity.tvShows.get(index[0]).getCover();
            final TextView tvShowTitle = new TextView(this);
            tvShowTitle.setText(abcSeasons.get(i).getTitle());
            tvShowTitle.setTextSize(30);
            tvShowTitle.setPadding(15,0,0,0);
            tvShowTitle.setId(i);
            ImageView tvShowImage = setTVShowImageDetails(tvShowCover);
            tvShowCellLayout.addView(tvShowImage);
            tvShowCellLayout.addView(tvShowTitle);
            tvShowHolderLayout.addView(tvShowCellLayout);
            tvShowTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcSeasons.get(tvShowTitle.getId()).getTv_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedActors() {
        for (int i=0 ; i<abcActors.size() ; i++) {
            LinearLayout tvShowCellLayout = new LinearLayout(this);
            tvShowCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcActors.get(i).getMedia_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            Bitmap tvShowCover = MainActivity.tvShows.get(index[0]).getCover();
            final TextView tvShowTitle = new TextView(this);
            tvShowTitle.setText(abcActors.get(i).getName());
            tvShowTitle.setTextSize(30);
            tvShowTitle.setPadding(15,0,0,0);
            tvShowTitle.setId(i);
            ImageView tvShowImage = setTVShowImageDetails(tvShowCover);
            tvShowCellLayout.addView(tvShowImage);
            tvShowCellLayout.addView(tvShowTitle);
            tvShowHolderLayout.addView(tvShowCellLayout);
            tvShowTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcActors.get(tvShowTitle.getId()).getMedia_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedDirectors() {
        for (int i=0 ; i<abcDirectors.size() ; i++) {
            LinearLayout tvShowCellLayout = new LinearLayout(this);
            tvShowCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap tvShowCover = abcDirectors.get(i).getCover();
            final TextView tvShowTitle = new TextView(this);
            tvShowTitle.setText(abcDirectors.get(i).getDirector());
            tvShowTitle.setTextSize(30);
            tvShowTitle.setPadding(15,0,0,0);
            tvShowTitle.setId(i);
            ImageView tvShowImage = setTVShowImageDetails(tvShowCover);
            tvShowCellLayout.addView(tvShowImage);
            tvShowCellLayout.addView(tvShowTitle);
            tvShowHolderLayout.addView(tvShowCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcDirectors.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            tvShowTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcDirectors.get(tvShowTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedTVShows() {
        for (int i=0 ; i<abcTVShows.size() ; i++) {
            LinearLayout tvShowCellLayout = new LinearLayout(this);
            tvShowCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap tvShowCover = abcTVShows.get(i).getCover();
            final TextView tvShowTitle = new TextView(this);
            tvShowTitle.setText(abcTVShows.get(i).getTitle());
            tvShowTitle.setTextSize(30);
            tvShowTitle.setPadding(15,0,0,0);
            tvShowTitle.setId(i);
            ImageView tvShowImage = setTVShowImageDetails(tvShowCover);
            tvShowCellLayout.addView(tvShowImage);
            tvShowCellLayout.addView(tvShowTitle);
            tvShowHolderLayout.addView(tvShowCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcTVShows.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            tvShowTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcTVShows.get(tvShowTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public void addTVShows() {
        for (int i=0 ; i<MainActivity.tvShows.size() ; i++) {
            LinearLayout tvShowCellLayout = new LinearLayout(this);
            tvShowCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap tvShowCover = MainActivity.tvShows.get(i).getCover();
            final TextView tvShowTitle = new TextView(this);
            tvShowTitle.setText(MainActivity.tvShows.get(i).getTitle());
            tvShowTitle.setTextSize(30);
            tvShowTitle.setPadding(15,0,0,0);
            tvShowTitle.setId(i);
            ImageView tvShowImage = setTVShowImageDetails(tvShowCover);
            tvShowCellLayout.addView(tvShowImage);
            tvShowCellLayout.addView(tvShowTitle);
            tvShowHolderLayout.addView(tvShowCellLayout);
            indexAndId.put(MainActivity.tvShows.get(i).get_id(), i);
            tvShowTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                    i.putExtra("index", tvShowTitle.getId());
                    i.putExtra("ID", MainActivity.tvShows.get(tvShowTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public ImageView setTVShowImageDetails(Bitmap movieCover) {
        ImageView tvShowImage = new ImageView(this);
        tvShowImage.setImageBitmap(movieCover);
        tvShowImage.setMaxWidth(150);
        tvShowImage.setMaxHeight(150);
        tvShowImage.setAdjustViewBounds(true);
        tvShowImage.setPadding(30,0,0,10);
        return tvShowImage;
    }
}

class tvShowCompare implements Comparator<TVShow> {
    @Override
    public int compare(TVShow tvShow, TVShow tvShow2) {
        if (tvShow.getTitle().compareTo(tvShow2.getTitle()) > 0) {
            return 1;
        } if (tvShow2.getTitle().compareTo(tvShow.getTitle()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class directorCompareTV implements Comparator<TVShow> {
    @Override
    public int compare(TVShow tvShow, TVShow tvShow2) {
        if (tvShow.getDirector().compareTo(tvShow2.getDirector()) > 0) {
            return 1;
        } if (tvShow2.getDirector().compareTo(tvShow.getDirector()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class seasonCompare implements Comparator<Season> {
    @Override
    public int compare(Season season, Season season2) {
        if (season.getTitle().compareTo(season2.getTitle()) > 0) {
            return 1;
        } if (season2.getTitle().compareTo(season.getTitle()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
