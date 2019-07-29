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

public class DetailedMusicScreen extends AppCompatActivity {

    private DBManager databaseMan;

    private LinearLayout tracksLayout;
    private LinearLayout addTracksLayout;
    private ImageButton addTracksButton;
    private final ReentrantLock lock = new ReentrantLock();
    private int lockCounter;

    static final EditText[] addTracks = new EditText[29];

    private EditText titleEdit;
    private EditText artistEdit;
    private EditText producerEdit;
    private EditText lengthEdit;
    private EditText descEdit;

    private ArrayList<Track> tracks;
    private ArrayList<Track> filteredTracks;
    private ArrayList<Integer> track_indexes = new ArrayList<>();
    private ArrayList<Integer> track_ids = new ArrayList<>();

    private ArrayList<EditText> trackTexts = new ArrayList<>();

    private Button editMusicButton;
    private Button deleteMusicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_music_screen);

        databaseMan = new DBManager(this, null, null, 1);

        Intent data = getIntent();
        final int index = data.getIntExtra("index",-1);
        final int _id = data.getIntExtra("ID", -1);

        tracks = MainActivity.tracks;

        tracksLayout = findViewById(R.id.tracksLayout);
        addTracksLayout = findViewById(R.id.addTracksLayoutEditScreen);
        addTracksButton = findViewById(R.id.addTracksButtonEditScreen);

        titleEdit = findViewById(R.id.musicTitleEdit);
        artistEdit = findViewById(R.id.musicArtistEdit);
        producerEdit = findViewById(R.id.musicProducerEdit);
        lengthEdit = findViewById(R.id.musicLengthEdit);
        descEdit = findViewById(R.id.musicDescEdit);

        editMusicButton = findViewById(R.id.editMusicButton);
        deleteMusicButton = findViewById(R.id.deleteMusicButton);

        Music music = MainActivity.music.get(index);
        titleEdit.setText(music.getTitle());
        artistEdit.setText(music.getArtist());
        producerEdit.setText(music.getProducer());
        lengthEdit.setText(String.valueOf(music.getLength()));
        descEdit.setText(music.getDescription());

        setTracks(_id);
        lock.lock();

        addTracksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (lock.isLocked()) {
                    lock.unlock();
                } addTrackEditText();
            }
        });

        deleteMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTracks(_id);
                deleteMusic(_id, index);
                Intent i = new Intent(getApplicationContext(), MusicScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Music deleted", Toast.LENGTH_SHORT).show();
            }
        });

        editMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMusic(_id, index);
                editTracks(track_ids, track_indexes);
                addTracks(_id);
                Intent i = new Intent(getApplicationContext(), MusicScreen.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Music updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteTracks(int _id) {
        databaseMan.removeTracks(_id);
        MainActivity.tracks.clear();
        MainActivity.counterTracks = 0;
        MainActivity.initData();
    }

    public void addTracks(int _id) {
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
            addTracksLayout.addView(addTracks[lockCounter]);
            lockCounter++;
            lock.lock();
        }
    }

    public void editTracks(ArrayList<Integer> track_id, ArrayList<Integer> track_index) {
        for (int i=0 ; i<filteredTracks.size() ; i++) {
            String trackName = trackTexts.get(i).getText().toString();
            databaseMan.editTrack(track_id.get(i), trackName);
            MainActivity.tracks.get(track_index.get(i)).setTitle(trackName);
        }
    }

    public void editMusic(int _id, int index) {
        String title = titleEdit.getText().toString();
        String artist = artistEdit.getText().toString();
        String producer = producerEdit.getText().toString();
        int length = Integer.parseInt(lengthEdit.getText().toString());
        String description = descEdit.getText().toString();
        databaseMan.updateMusic(_id, title, artist, producer, length, description);
        Music music = MainActivity.music.get(index);
        music.setTitle(title);
        music.setArtist(artist);
        music.setProducer(producer);
        music.setLength(length);
        music.setDescription(description);
    }

    public void deleteMusic(int _id, int index) {
        databaseMan.removeMusic(_id);
        MainActivity.music.remove(index);
    }

    public void setTracks(int _id) {
        filteredTracks = new ArrayList<>();
        for (int i=0 ; i<tracks.size() ; i++) {
            if (tracks.get(i).getMusic_id() == _id) {
                Track track = new Track(tracks.get(i).get_id(), tracks.get(i).getTitle(), tracks.get(i).getMusic_id());
                filteredTracks.add(track);
                track_indexes.add(i);
                track_ids.add(tracks.get(i).get_id());
            }
        }
        for (int i = 0 ; i<filteredTracks.size() ; i++) {
            EditText trackText = new EditText(this);
            trackText.setText(filteredTracks.get(i).getTitle());
            trackText.setTextSize(18);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100, 0, 40, 0);
            trackText.setLayoutParams(params);
            trackText.setId(i);
            trackTexts.add(trackText);
            tracksLayout.addView(trackTexts.get(i));
        }

    }

}
