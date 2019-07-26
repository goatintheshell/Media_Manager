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

public class MusicScreen extends AppCompatActivity {

    LinearLayout musicHolderLayout;
    Button addNewMusicButton;
    Button returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        musicHolderLayout = findViewById(R.id.musicHolderLayout);
        addNewMusicButton = findViewById(R.id.addNewMusicButton);

        returnHomeButton = findViewById(R.id.returnToHomeMusic);

        addMusic();

        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        addNewMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMusicScreen.class);
                startActivity(i);
            }
        });

    }

    public void addMusic() {
        for (int i=0 ; i<MainActivity.music.size() ; i++) {
            LinearLayout musicCellLayout = new LinearLayout(this);
            musicCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap musicCover = MainActivity.music.get(i).getCover();
            final TextView musicTitle = new TextView(this);
            musicTitle.setText(MainActivity.music.get(i).getTitle());
            musicTitle.setTextSize(30);
            musicTitle.setPadding(15,0,0,0);
            musicTitle.setId(i);
            ImageView musicImage = setMusicImageDetails(musicCover);
            musicCellLayout.addView(musicImage);
            musicCellLayout.addView(musicTitle);
            musicHolderLayout.addView(musicCellLayout);

            musicTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMusicScreen.class);
                    i.putExtra("index", musicTitle.getId());
                    i.putExtra("ID", MainActivity.music.get(musicTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public ImageView setMusicImageDetails(Bitmap musicCover) {
        ImageView musicImage = new ImageView(this);
        musicImage.setImageBitmap(musicCover);
        musicImage.setMaxWidth(150);
        musicImage.setMaxHeight(150);
        musicImage.setAdjustViewBounds(true);
        musicImage.setPadding(30,0,0,10);
        return musicImage;
    }
}


