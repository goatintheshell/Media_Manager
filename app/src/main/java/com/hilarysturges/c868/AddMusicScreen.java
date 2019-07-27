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
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.locks.ReentrantLock;

public class AddMusicScreen extends AppCompatActivity {

    private LinearLayout tracksLayout;
    private ImageButton addTracksButton;
    private final ReentrantLock lock = new ReentrantLock();
    private int lockCounter;

    static DBManager databaseMan;

    static final EditText[] addTracks = new EditText[29];

    RadioButton record;
    RadioButton cassette;
    RadioButton cd;
    EditText titleEdit;
    EditText artistEdit;
    EditText producerEdit;
    EditText lengthEdit;
    EditText descriptionEdit;
    Button addMusicButton;

    Bitmap cdBMP;
    Bitmap cassetteBMP;
    Bitmap recordBMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music_screen);

        databaseMan = new DBManager(getApplicationContext(), null, null, 1);

        tracksLayout = findViewById(R.id.addTracksLayout);
        addTracksButton = findViewById(R.id.addTracksButton);

        cd = findViewById(R.id.musicCD);
        cassette = findViewById(R.id.musicCassette);
        record = findViewById(R.id.musicRecord);
        titleEdit = findViewById(R.id.musicTitle);
        artistEdit = findViewById(R.id.musicArtist);
        producerEdit = findViewById(R.id.musicProducer);
        lengthEdit = findViewById(R.id.musicLength);
        descriptionEdit = findViewById(R.id.musicDesc);
        addMusicButton = findViewById(R.id.addMusicButton);

        cdBMP = BitmapFactory.decodeResource(getResources(), R.drawable.cd);
        cassetteBMP = BitmapFactory.decodeResource(getResources(), R.drawable.cassette);
        recordBMP = BitmapFactory.decodeResource(getResources(), R.drawable.record);

        lock.lock();

        addTracksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lock.isLocked()) {
                    lock.unlock();
                } addTrackEditText();
            }
        });

        addMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!cd.isChecked()) && (!cassette.isChecked()) && (!record.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (titleEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (producerEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (lengthEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (artistEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (descriptionEdit.getText().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    addMusic();
                    addTracks();
                    Intent i = new Intent(getApplicationContext(), MusicScreen.class);
                    startActivity(i);
                }
            }
        });
    }

    public void addMusic() {
        String title = titleEdit.getText().toString();
        String producer = producerEdit.getText().toString();
        int length = Integer.parseInt(lengthEdit.getText().toString());
        String artist = artistEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        int type = getType();
        Bitmap cover = getCover(type);
        Music music = new Music(title, artist, producer, length, cover, type, description);
        databaseMan.addMusic(music, getNumTracks());
        Music music1 = databaseMan.getLastMusic();
        MainActivity.music.add(music1);
    }

    public Bitmap getCover(int type) {
        if (type==1)
            return cassetteBMP;
        if (type==2)
            return cdBMP;
        else
            return recordBMP;
    }

    public int getType() {
        int type = 0;
        if (cassette.isChecked())
            type++;
        if (cd.isChecked())
            type=2;
        return type;
    }

    public int getNumTracks() {
        int numTracks = 0;
        for (int i=0 ; i<29 ; i++) {
            if (addTracks[i]!=null)
                numTracks++;
        }
        return numTracks;
    }

    public void addTracks() {
        int _id = MainActivity.music.get(MainActivity.music.size()-1).get_id();
        for (int i=0 ; i<29 ; i++) {
            if (addTracks[i]!=null)
                if (!addTracks[i].getText().toString().isEmpty()) {
                    String trackName = addTracks[i].getText().toString();
                    databaseMan.addTrack(trackName, _id);
                    Track track = databaseMan.getLastTrack();
                    MainActivity.tracks.add(track);
                }
        }
    }

    public void addTrackEditText() {
        while ((!lock.isLocked()) && addTracks[28] == null) {
            addTracks[lockCounter] = new EditText(getApplicationContext());
            addTracks[lockCounter].setHint("Track Title");
            tracksLayout.addView(addTracks[lockCounter]);
            lockCounter++;
            lock.lock();
        }
    }
}
