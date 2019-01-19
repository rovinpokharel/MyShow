package com.rovin.pokharel.myshow;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rovin.pokharel.myshow.helper.SessionManager;
import com.squareup.picasso.Picasso;

public class MoviesDescription extends AppCompatActivity {
    private ImageView movieImage;
    private TextView movieName, movieDescription, movieDate;
    private TextView movieType;
    private FloatingActionButton fabTicket;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_description);

        sessionManager = new SessionManager(getApplicationContext());

        movieImage = (ImageView)findViewById(R.id.desc_movie_image);
        movieName = (TextView) findViewById(R.id.desc_movie_name);
        movieDescription = (TextView)findViewById(R.id.desc_movie_description);
        movieDate = (TextView)findViewById(R.id.desc_movie_date);
        movieType = (TextView)findViewById(R.id.desc_movie_type);
        fabTicket = (FloatingActionButton) findViewById(R.id.fab_ticket);


        movieName.setText(getIntent().getStringExtra(ConstantData.MOVIE_NAME));
        movieDate.setText(getIntent().getStringExtra(ConstantData.MOVIE_DATE));
        movieDescription.setText(getIntent().getStringExtra(ConstantData.MOVIE_DESCRIPTION));
        movieType.setText(getIntent().getStringExtra(ConstantData.MOVIE_TYPE));
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra(ConstantData.MOVIE_IMAGE)).into(movieImage);

        if (movieType.getText().equals("Now Showing")){
            movieType.setTextColor(Color.GREEN);
        }

        movieType.setOnClickListener(new View.OnClickListener() {
            String id = getIntent().getStringExtra(ConstantData.MOVIE_ID);

            @Override
            public void onClick(View v) {
                if (!movieType.getText().equals("Now Showing") && sessionManager.isAdmin()){
                    movieType.setText("Now Showing");
                    movieType.setTextColor(Color.GREEN);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("movies").child(id).child("movieType")
                            .setValue("Now Showing");
                }

            }
        });


        fabTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviesDescription.this, BookingActivity.class);
                intent.putExtra(ConstantData.MOVIE_IMAGE, getIntent().getStringExtra(ConstantData.MOVIE_IMAGE));
                startActivity(intent);
            }
        });

    }
}
