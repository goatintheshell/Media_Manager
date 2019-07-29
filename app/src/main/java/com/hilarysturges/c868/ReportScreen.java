package com.hilarysturges.c868;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;

public class ReportScreen extends AppCompatActivity {


    TableLayout tableLayout;
    Spinner spinner;
    Button returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);

        tableLayout = findViewById(R.id.tableLayout);
        spinner = findViewById(R.id.spinner);
        returnHomeButton = findViewById(R.id.returnToHomeReports);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.media_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clearDisplay();
                selectTable(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void clearDisplay() {
        tableLayout.removeAllViews();
    }

    public void selectTable(String position) {
        if (position.equals("Music"))
            displayTableMusic(MainActivity.music);
        if (position.equals("Movies"))
            displayTableMovies(MainActivity.movies);
        if (position.equals("TV Shows"))
            displayTableTVShows(MainActivity.tvShows);
        if (position.equals("Tracks"))
            displayTableTracks(MainActivity.tracks);
        if (position.equals("Actors"))
            displayTableActors(MainActivity.actors);
        if (position.equals("Seasons"))
            displayTableSeasons(MainActivity.seasons);
    }

    public void displayTableMusic(ArrayList<Music> music) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView titleDescText = new TextView(this);
        titleDescText.setPadding(10,10,0,0);
        titleDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleDescText.setText("Title");
        TextView artistDescText = new TextView(this);
        artistDescText.setPadding(10,10,0,0);
        artistDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        artistDescText.setText("Artist");
        TextView lengthDescText = new TextView(this);
        lengthDescText.setPadding(10,10,0,0);
        lengthDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        lengthDescText.setText("Length");
        TextView prodDescText = new TextView(this);
        prodDescText.setPadding(10,10,0,0);
        prodDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        prodDescText.setText("Producer");
        TextView descDescText = new TextView(this);
        descDescText.setPadding(10,10,0,0);
        descDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        descDescText.setText("Description");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(titleDescText);
        tableRowDesc.addView(artistDescText);
        tableRowDesc.addView(lengthDescText);
        tableRowDesc.addView(prodDescText);
        tableRowDesc.addView(descDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<music.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = music.get(i).getCover();
            ImageView coverView = setImageViewDetails(cover);
            TextView titleText = new TextView(this);
            titleText.setPadding(10,10,0,0);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setText(music.get(i).getTitle());
            TextView artistText = new TextView(this);
            artistText.setPadding(10,10,0,0);
            artistText.setGravity(Gravity.CENTER_HORIZONTAL);
            artistText.setText(music.get(i).getArtist());
            TextView lengthText = new TextView(this);
            lengthText.setPadding(10,10,0,0);
            lengthText.setGravity(Gravity.CENTER_HORIZONTAL);
            lengthText.setText(String.valueOf(music.get(i).getLength()));
            TextView producerText = new TextView(this);
            producerText.setPadding(10,10,0,0);
            producerText.setGravity(Gravity.CENTER_HORIZONTAL);
            producerText.setText(music.get(i).getProducer());
            TextView descText = new TextView(this);
            descText.setPadding(10,10,0,0);
            descText.setGravity(Gravity.CENTER_HORIZONTAL);
            descText.setText(music.get(i).getDescription());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(music.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(titleText);
            tableRow.addView(artistText);
            tableRow.addView(lengthText);
            tableRow.addView(producerText);
            tableRow.addView(descText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public void displayTableMovies(ArrayList<Movie> movies) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView titleDescText = new TextView(this);
        titleDescText.setPadding(10,10,0,0);
        titleDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleDescText.setText("Title");
        TextView directorDescText = new TextView(this);
        directorDescText.setPadding(10,10,0,0);
        directorDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        directorDescText.setText("Director");
        TextView lengthDescText = new TextView(this);
        lengthDescText.setPadding(10,10,0,0);
        lengthDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        lengthDescText.setText("Length");
        TextView ratingDescText = new TextView(this);
        ratingDescText.setPadding(10,10,0,0);
        ratingDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        ratingDescText.setText("Rating");
        TextView descDescText = new TextView(this);
        descDescText.setPadding(10,10,0,0);
        descDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        descDescText.setText("Description");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(titleDescText);
        tableRowDesc.addView(directorDescText);
        tableRowDesc.addView(lengthDescText);
        tableRowDesc.addView(ratingDescText);
        tableRowDesc.addView(descDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<movies.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = movies.get(i).getCover();
            ImageView coverView = setImageViewDetails(cover);
            TextView titleText = new TextView(this);
            titleText.setPadding(10,10,0,0);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setText(movies.get(i).getTitle());
            TextView directorText = new TextView(this);
            directorText.setPadding(10,10,0,0);
            directorText.setGravity(Gravity.CENTER_HORIZONTAL);
            directorText.setText(movies.get(i).getDirector());
            TextView lengthText = new TextView(this);
            lengthText.setPadding(10,10,0,0);
            lengthText.setGravity(Gravity.CENTER_HORIZONTAL);
            lengthText.setText(String.valueOf(movies.get(i).getLength()));
            TextView ratingText = new TextView(this);
            ratingText.setPadding(10,10,0,0);
            ratingText.setGravity(Gravity.CENTER_HORIZONTAL);
            ratingText.setText(movies.get(i).getRating());
            TextView descText = new TextView(this);
            descText.setPadding(10,10,0,0);
            descText.setGravity(Gravity.CENTER_HORIZONTAL);
            descText.setText(movies.get(i).getDescription());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(movies.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(titleText);
            tableRow.addView(directorText);
            tableRow.addView(lengthText);
            tableRow.addView(ratingText);
            tableRow.addView(descText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public void displayTableTVShows(ArrayList<TVShow> tvShows) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView titleDescText = new TextView(this);
        titleDescText.setPadding(10,10,0,0);
        titleDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleDescText.setText("Title");
        TextView directorDescText = new TextView(this);
        directorDescText.setPadding(10,10,0,0);
        directorDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        directorDescText.setText("Director");
        TextView descDescText = new TextView(this);
        descDescText.setPadding(10,10,0,0);
        descDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        descDescText.setText("Description");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(titleDescText);
        tableRowDesc.addView(directorDescText);
        tableRowDesc.addView(descDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<tvShows.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = tvShows.get(i).getCover();
            ImageView coverView = setImageViewDetails(cover);
            TextView titleText = new TextView(this);
            titleText.setPadding(10,10,0,0);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setText(tvShows.get(i).getTitle());
            TextView directorText = new TextView(this);
            directorText.setPadding(10,10,0,0);
            directorText.setGravity(Gravity.CENTER_HORIZONTAL);
            directorText.setText(tvShows.get(i).getDirector());
            TextView descText = new TextView(this);
            descText.setPadding(10,10,0,0);
            descText.setGravity(Gravity.CENTER_HORIZONTAL);
            descText.setText(tvShows.get(i).getDescription());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(tvShows.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(titleText);
            tableRow.addView(directorText);
            tableRow.addView(descText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public void displayTableTracks(ArrayList<Track> tracks) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView titleDescText = new TextView(this);
        titleDescText.setPadding(10,10,0,0);
        titleDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleDescText.setText("Title");
        TextView assocMediaDescText = new TextView(this);
        assocMediaDescText.setPadding(10,10,0,0);
        assocMediaDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        assocMediaDescText.setText("Linked To");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(titleDescText);
        tableRowDesc.addView(assocMediaDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<tracks.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = BitmapFactory.decodeResource(getResources(),R.drawable.track);
            ImageView coverView = setImageViewDetails(cover);
            TextView titleText = new TextView(this);
            titleText.setPadding(10,10,0,0);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setText(tracks.get(i).getTitle());
            TextView assocMediaText = new TextView(this);
            assocMediaText.setPadding(10,10,0,0);
            assocMediaText.setGravity(Gravity.CENTER_HORIZONTAL);
            assocMediaText.setText(tracks.get(i).getMusic().getTitle());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(tracks.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(titleText);
            tableRow.addView(assocMediaText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public void displayTableActors(ArrayList<Actor> actors) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView nameDescText = new TextView(this);
        nameDescText.setPadding(10,10,0,0);
        nameDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        nameDescText.setText("Name");
        TextView assocMediaDescText = new TextView(this);
        assocMediaDescText.setPadding(10,10,0,0);
        assocMediaDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        assocMediaDescText.setText("Linked To");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(nameDescText);
        tableRowDesc.addView(assocMediaDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<actors.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = BitmapFactory.decodeResource(getResources(),R.drawable.actor);
            ImageView coverView = setImageViewDetails(cover);
            TextView nameText = new TextView(this);
            nameText.setPadding(10,10,0,0);
            nameText.setGravity(Gravity.CENTER_HORIZONTAL);
            nameText.setText(actors.get(i).getName());
            TextView assocMediaText = new TextView(this);
            assocMediaText.setPadding(10,10,0,0);
            assocMediaText.setGravity(Gravity.CENTER_HORIZONTAL);
            if (actors.get(i).getMovie()==null)
                assocMediaText.setText(actors.get(i).getTvShow().getTitle());
            if (actors.get(i).getTvShow()==null)
                assocMediaText.setText(actors.get(i).getMovie().getTitle());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(actors.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(nameText);
            tableRow.addView(assocMediaText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public void displayTableSeasons(ArrayList<Season> seasons) {
        TableRow tableRowDesc = new TableRow(this);
        TextView coverDescText = new TextView(this);
        coverDescText.setText("Cover");
        TextView titleDescText = new TextView(this);
        titleDescText.setPadding(10,10,0,0);
        titleDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleDescText.setText("Title");
        TextView assocMediaDescText = new TextView(this);
        assocMediaDescText.setPadding(10,10,0,0);
        assocMediaDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        assocMediaDescText.setText("Linked To");
        TextView addedDescText = new TextView(this);
        addedDescText.setPadding(10,10,0,0);
        addedDescText.setGravity(Gravity.CENTER_HORIZONTAL);
        addedDescText.setText("Added");
        tableRowDesc.addView(coverDescText);
        tableRowDesc.addView(titleDescText);
        tableRowDesc.addView(assocMediaDescText);
        tableRowDesc.addView(addedDescText);
        tableLayout.addView(tableRowDesc);
        for (int i=0 ; i<seasons.size() ; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,10);
            Bitmap cover = BitmapFactory.decodeResource(getResources(),R.drawable.season);
            ImageView coverView = setImageViewDetails(cover);
            TextView titleText = new TextView(this);
            titleText.setPadding(10,10,0,0);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setText(seasons.get(i).getTitle());
            TextView assocMediaText = new TextView(this);
            assocMediaText.setPadding(10,10,0,0);
            assocMediaText.setGravity(Gravity.CENTER_HORIZONTAL);
            assocMediaText.setText(seasons.get(i).getTvShow().getTitle());
            TextView addedText = new TextView(this);
            addedText.setPadding(10,10,0,0);
            addedText.setGravity(Gravity.CENTER_HORIZONTAL);
            addedText.setText(String.valueOf(seasons.get(i).getAdded()));
            tableRow.addView(coverView);
            tableRow.addView(titleText);
            tableRow.addView(assocMediaText);
            tableRow.addView(addedText);
            tableLayout.addView(tableRow);
        }
    }

    public ImageView setImageViewDetails(Bitmap cover) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(cover);
        imageView.setMaxWidth(150);
        imageView.setMaxHeight(150);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }
}
