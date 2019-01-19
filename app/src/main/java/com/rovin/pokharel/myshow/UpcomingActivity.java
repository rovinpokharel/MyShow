package com.rovin.pokharel.myshow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rovin.pokharel.myshow.model.Movie;
import com.rovin.pokharel.myshow.model.MovieAdapter;

import java.util.ArrayList;

public class UpcomingActivity extends AppCompatActivity {

    private ArrayList<Movie> addMoviesList = new ArrayList<>();;
    private MovieAdapter movieAdapter;

    private Movie movie;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        //recycler view initialization
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.hasFixedSize();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference("movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String id = snapshot.child("movieID").getValue().toString();
                    String name = snapshot.child("movieName").getValue().toString();
                    String date = snapshot.child("movieDate").getValue().toString();
                    String image = snapshot.child("movieImage").getValue().toString();
                    String description = snapshot.child("movieDescription").getValue().toString();
                    String type = snapshot.child("movieType").getValue().toString();
//                    AddItem item = snapshot.getValue(AddItem.class);


                    movie = new Movie(id, name, date, image, description, type);
                    addMoviesList.add(movie);

                }
                movieAdapter = new MovieAdapter(getApplicationContext(), addMoviesList);
                recyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Intent intent = new Intent(UpcomingActivity.this, MoviesDescription.class);
                intent.putExtra(ConstantData.MOVIE_ID, addMoviesList.get(position).getMovieID());
                intent.putExtra(ConstantData.MOVIE_NAME, addMoviesList.get(position).getMovieName());
                intent.putExtra(ConstantData.MOVIE_DATE, addMoviesList.get(position).getMovieDate());
                intent.putExtra(ConstantData.MOVIE_DESCRIPTION, addMoviesList.get(position).getMovieDescription());
                intent.putExtra(ConstantData.MOVIE_IMAGE, addMoviesList.get(position).getMovieImage());
                intent.putExtra(ConstantData.MOVIE_TYPE, addMoviesList.get(position).getMovieType());
                startActivity(intent);

            }
        }));

    }
}
