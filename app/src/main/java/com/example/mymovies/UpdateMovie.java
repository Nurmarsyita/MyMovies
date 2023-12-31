package com.example.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateMovie extends AppCompatActivity {

    TextView tvMovieTitle, tvMovieGenre, tvYear, tvMovieRating, tvID;
    EditText etMovieTitle, etMovieGenre, etMovieYear, etID;
    Spinner spinnerMovieRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);

        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieGenre = findViewById(R.id.tvMovieGenre);
        tvYear = findViewById(R.id.tvYear);
        tvMovieRating = findViewById(R.id.tvMovieRating);
        tvID = findViewById(R.id.tvID);

        etMovieTitle = findViewById(R.id.etMovieTitle);
        etMovieGenre = findViewById(R.id.etMovieGenre);
        etMovieYear = findViewById(R.id.etYear);
        etID = findViewById(R.id.etID);

        spinnerMovieRating = findViewById(R.id.spinnerMovieRating);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        etID.setFocusable(false);
        etID.setText(String.valueOf(data.getId()));
        etMovieTitle.setText(data.getTitle());
        etMovieGenre.setText(data.getGenre());
        etMovieYear.setText(String.valueOf(data.getYear()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(UpdateMovie.this);
                String selectedItem = spinnerMovieRating.getSelectedItem().toString();
                data.setTitle(etMovieTitle.getText().toString());
                data.setGenre(etMovieGenre.getText().toString());
                data.setYear(Integer.parseInt(etMovieYear.getText().toString()));
                data.setRating(selectedItem);
                db.updateMovie(data);
                db.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(UpdateMovie.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle()+"?");
                myBuilder.setCancelable(true);

                myBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(UpdateMovie.this);
                        dbh.deleteMovie(data.getId());
                        finish();

                    }
                });
//                myBuilder.setNeutralButton("Delete", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(UpdateMovie.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(true);

                //configure the 'positive' button
                myBuilder.setPositiveButton("Do not discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                //configure the 'negative' button
                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}