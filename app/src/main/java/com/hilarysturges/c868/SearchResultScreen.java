package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class SearchResultScreen extends AppCompatActivity {

    LinearLayout resultsLayout;
    Button returnToHome;

    ArrayList<Object> filteredResults = new ArrayList<>();
    ArrayList<TextView> resultTexts = new ArrayList<>();
    ArrayList<ImageView> resultImageViews = new ArrayList<>();
    ArrayList<LinearLayout> resultCellLayouts = new ArrayList<>();
    ImageView resultImageView;
    LinearLayout resultCellLayout;
    Bitmap resultCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_screen);

        resultCover = BitmapFactory.decodeResource(getResources(), R.drawable.blank);

        resultsLayout = findViewById(R.id.searchResultsLayout);
        returnToHome = findViewById(R.id.returnToHomeSearch);

        clearResults();

        ArrayList<ArrayList> result = MainActivity.searchResult;

        for (int i=0 ; i<result.size() ; i++) {
            ArrayList separatedResult = result.get(i);
            this.filteredResults.addAll(separatedResult);
        }

        addResults(filteredResults);
        MainActivity.searchResult.clear();

        returnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void clearResults() {
        if (resultCellLayout!=null)
            resultCellLayout.removeAllViews();
        resultsLayout.removeAllViews();
        filteredResults.clear();
        resultTexts.clear();
        resultImageViews.clear();
        resultCellLayouts.clear();
    }

    public void addResults(final ArrayList result) {
        for (int i=0 ; i<result.size() ; i++) {
            if (result.get(i) instanceof Actor) {
                setActorResult(result.get(i));
            } if (result.get(i) instanceof Music){
                setMusicResult(result.get(i));
            } if (result.get(i) instanceof  Movie) {
                setMovieResult(result.get(i));
            } if (result.get(i) instanceof  TVShow) {
                setTVShowResult(result.get(i));
            } if (result.get(i) instanceof Track) {
                setTrackResult(result.get(i));
            } if (result.get(i) instanceof Season) {
                setSeasonResult(result.get(i));
            }
        }
        for (int i=0 ; i<resultTexts.size() ; i++) {
            resultCellLayout = new LinearLayout(this);
            resultCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            resultCellLayouts.add(resultCellLayout);
            resultTexts.get(i).setTextSize(30);
            resultTexts.get(i).setPadding(15, 0, 0, 0);
            resultCellLayout.addView(resultImageViews.get(i));
            resultCellLayout.addView(resultTexts.get(i));
            resultsLayout.addView(resultCellLayouts.get(i));
        }

    }

    public void setActorResult(Object result) {
        final int index = ((Actor) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((Actor) result).getName());
        resultCover = BitmapFactory.decodeResource(getResources(), R.drawable.actor);
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.actors.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public void setMusicResult(Object result) {
        final int index = ((Music) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((Music) result).getTitle());
        resultCover = ((Music) result).getCover();
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedMusicScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.music.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public void setMovieResult(Object result) {
        final int index = ((Movie) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((Movie) result).getTitle());
        resultCover = ((Movie) result).getCover();
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedMovieScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.movies.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public void setTVShowResult(Object result) {
        final int index = ((TVShow) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((TVShow) result).getTitle());
        resultCover = ((TVShow) result).getCover();
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.tvShows.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public void setTrackResult(Object result) {
        final int index = ((Track) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((Track) result).getTitle());
        resultCover = BitmapFactory.decodeResource(getResources(), R.drawable.track);
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedMusicScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.tracks.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public void setSeasonResult(Object result) {
        final int index = ((Season) result).getIndex();
        TextView resultText = new TextView(this);
        resultImageView = new ImageView(this);
        resultText.setText(((Season) result).getTitle());
        resultCover = BitmapFactory.decodeResource(getResources(), R.drawable.season);
        resultImageView = setResultImageDetails(resultCover);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailedTVShowScreen.class);
                i.putExtra("index", index);
                i.putExtra("ID", MainActivity.seasons.get(index).get_id());
                startActivity(i);
            }
        });
        resultTexts.add(resultText);
        resultImageViews.add(resultImageView);
    }

    public ImageView setResultImageDetails(Bitmap resultCover) {
        ImageView resultImage = new ImageView(this);
        resultImage.setImageBitmap(resultCover);
        resultImage.setMaxWidth(150);
        resultImage.setMaxHeight(150);
        resultImage.setAdjustViewBounds(true);
        resultImage.setPadding(30,0,0,10);
        return resultImage;
    }
}
