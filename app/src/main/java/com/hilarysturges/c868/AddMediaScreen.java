package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMediaScreen extends AppCompatActivity {

    private Button musicButton;
    private Button movieButton;
    private Button tvShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media_screen);

        musicButton = findViewById(R.id.goMusic);
        movieButton = findViewById(R.id.goMovie);
        tvShowButton = findViewById(R.id.goTVShow);

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMusicScreen.class);
                startActivity(i);
            }
        });

        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMovieScreen.class);
                startActivity(i);
            }
        });

        tvShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTVShowScreen.class);
                startActivity(i);
            }
        });
    }
}
