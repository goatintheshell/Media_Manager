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

public class MusicScreen extends AppCompatActivity {

    LinearLayout musicHolderLayout;
    Button addNewMusicButton;
    Button returnHomeButton;

    TextView browseArtist;
    TextView browseTrack;
    TextView browseABC;

    HashMap<Integer, Integer> indexAndId = new HashMap<>();

    ArrayList<Music> abcMusic = new ArrayList<>();
    ArrayList<Music> abcArtists = new ArrayList<>();
    ArrayList<Track> abcTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        abcMusic.addAll(MainActivity.music);
        abcArtists.addAll(MainActivity.music);
        abcTracks.addAll(MainActivity.tracks);

        musicHolderLayout = findViewById(R.id.musicHolderLayout);
        addNewMusicButton = findViewById(R.id.addNewMusicButton);
        returnHomeButton = findViewById(R.id.returnToHomeMusic);

        browseArtist = findViewById(R.id.browseByArtistMusic);
        browseTrack = findViewById(R.id.browseByTrackMusic);
        browseABC = findViewById(R.id.browseAlphabeticallyMusic);

        addMusic();

        browseArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeArtists();
                clearMusic();
                setAlphabetizedArtists();
            }
        });

        browseTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeTracks();
                clearMusic();
                setAlphabetizedTracks();
            }
        });

        browseABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alphabetizeMusic();
                clearMusic();
                setAlphabetizedMusic();
            }
        });

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

    public void alphabetizeArtists() {
        Collections.sort(abcArtists, new artistCompare());
    }

    public void alphabetizeMusic() {
        Collections.sort(abcMusic, new musicCompare());
    }

    public void alphabetizeTracks() {
        Collections.sort(abcTracks, new trackCompare());
    }

    public void clearMusic() {
        musicHolderLayout.removeAllViews();
    }

    public void setAlphabetizedTracks() {
        for (int i=0 ; i<abcTracks.size() ; i++) {
            LinearLayout musicCellLayout = new LinearLayout(this);
            musicCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcTracks.get(i).getMusic_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            Bitmap musicCover = MainActivity.music.get(index[0]).getCover();
            final TextView musicTitle = new TextView(this);
            musicTitle.setText(abcTracks.get(i).getTitle());
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
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcTracks.get(musicTitle.getId()).getMusic_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedArtists() {
        for (int i=0 ; i<abcArtists.size() ; i++) {
            LinearLayout musicCellLayout = new LinearLayout(this);
            musicCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap musicCover = abcArtists.get(i).getCover();
            final TextView musicTitle = new TextView(this);
            musicTitle.setText(abcArtists.get(i).getArtist());
            musicTitle.setTextSize(30);
            musicTitle.setPadding(15,0,0,0);
            musicTitle.setId(i);
            ImageView musicImage = setMusicImageDetails(musicCover);
            musicCellLayout.addView(musicImage);
            musicCellLayout.addView(musicTitle);
            musicHolderLayout.addView(musicCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcArtists.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            musicTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMusicScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcArtists.get(musicTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
    }

    public void setAlphabetizedMusic() {
        for (int i=0 ; i<abcMusic.size() ; i++) {
            LinearLayout musicCellLayout = new LinearLayout(this);
            musicCellLayout.setOrientation(LinearLayout.HORIZONTAL);
            Bitmap musicCover = abcMusic.get(i).getCover();
            final TextView musicTitle = new TextView(this);
            musicTitle.setText(abcMusic.get(i).getTitle());
            musicTitle.setTextSize(30);
            musicTitle.setPadding(15,0,0,0);
            musicTitle.setId(i);
            ImageView musicImage = setMusicImageDetails(musicCover);
            musicCellLayout.addView(musicImage);
            musicCellLayout.addView(musicTitle);
            musicHolderLayout.addView(musicCellLayout);
            final int[] index = new int[1];
            for ( int key : indexAndId.keySet() ) {
                if (key == abcMusic.get(i).get_id()) {
                    index[0] = indexAndId.get(key);
                }
            }
            musicTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DetailedMusicScreen.class);
                    i.putExtra("index", index[0]);
                    i.putExtra("ID", abcMusic.get(musicTitle.getId()).get_id());
                    startActivity(i);
                }
            });
        }
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
            indexAndId.put(MainActivity.music.get(i).get_id(), i);
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

class musicCompare implements Comparator<Music> {
    @Override
    public int compare(Music music, Music music2) {
        if (music.getTitle().compareTo(music2.getTitle()) > 0) {
            return 1;
        } if (music2.getTitle().compareTo(music.getTitle()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class artistCompare implements Comparator<Music> {
    @Override
    public int compare(Music music, Music music2) {
        if (music.getArtist().compareTo(music2.getArtist()) > 0) {
            return 1;
        } if (music2.getArtist().compareTo(music.getArtist()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

class trackCompare implements Comparator<Track> {
    @Override
    public int compare(Track track, Track track2) {
        if (track.getTitle().compareTo(track2.getTitle()) > 0) {
            return 1;
        } if (track2.getTitle().compareTo(track.getTitle()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
