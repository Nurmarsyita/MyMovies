package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayMovie extends AppCompatActivity {

    Button btnPg13;
    ListView lv;

    ArrayList<Movie> alMovies;
    ArrayList<Movie> filteredSongList;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        lv = findViewById(R.id.lv);
        btnPg13 = findViewById(R.id.btnPg13);

        DBHelper db = new DBHelper(DisplayMovie.this);
        alMovies = db.getMovie();
        db.close();

        adapter = new CustomAdapter(this, R.layout.row, alMovies);
        lv.setAdapter(adapter);

        btnPg13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(DisplayMovie.this);
                alMovies.clear();

                String filterText = "PG-13";
                alMovies.addAll(dbh.getPg13movies(filterText));

                adapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie target = alMovies.get(position);

                Intent intent = new Intent(DisplayMovie.this, UpdateMovie.class);
                intent.putExtra("data", target);
                startActivity(intent);
            }
        });


    }
}