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

public class TVShowsScreen extends AppCompatActivity {

    LinearLayout tvShowHolderLayout;
    Button addNewTVShowButton;
    Button returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_screen);

        tvShowHolderLayout = findViewById(R.id.tvShowHolderLayout);
        addNewTVShowButton = findViewById(R.id.addNewTVShowButton);

        returnHomeButton = findViewById(R.id.returnToHomeTVShows);

        addTVShows();

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


