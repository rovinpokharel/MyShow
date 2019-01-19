package com.rovin.pokharel.myshow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rovin.pokharel.myshow.model.Movie;
import com.rovin.pokharel.myshow.model.MovieAdapter;
import com.rovin.pokharel.myshow.model.ShowAdapter;
import com.rovin.pokharel.myshow.model.ShowTimeAdapter;
import com.rovin.pokharel.myshow.model.Time;

import java.util.ArrayList;

public class NowShowing extends AppCompatActivity {
    private RecyclerView movieRecyclerView;


    private Movie movie;
    private ShowAdapter showAdapter;
    private ArrayList<Movie> addMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_showing);

        //recycler view initialization
        movieRecyclerView = (RecyclerView) findViewById(R.id.nowshowing_recyclerview);
        movieRecyclerView.hasFixedSize();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        movieRecyclerView.setLayoutManager(layoutManager);

        //querying firebase to list now showing movies
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("movies").orderByChild("movieType").equalTo("Now Showing");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addMoviesList = new ArrayList<>();

                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        String name = snapshot.child("movieName").getValue().toString();
                        String date = snapshot.child("movieDate").getValue().toString();
                        String image = snapshot.child("movieImage").getValue().toString();
                        String description = snapshot.child("movieDescription").getValue().toString();

                        movie = new Movie(name, date, image, description);
                        addMoviesList.add(movie);
                    }

                    showAdapter = new ShowAdapter(getApplicationContext(), addMoviesList);
                    movieRecyclerView.setAdapter(showAdapter);
                    showAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        showTime();

    }

    private void showTime(){

        ArrayList<Time> timeArrayList = new ArrayList<>();
        timeArrayList.add(new Time("7:00 AM"));
        timeArrayList.add(new Time("11:00 AM"));
        timeArrayList.add(new Time("3:00 PM"));
        timeArrayList.add(new Time("7:00 PM"));

        LayoutInflater inflater=   (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rowitem_nowshowing,null);

        ListView timeView = (ListView) view.findViewById(R.id.nowshowing_showtime);

        ShowTimeAdapter showTimeAdapter = new ShowTimeAdapter(getApplicationContext(), timeArrayList);
        timeView.setAdapter(showTimeAdapter);

    }
}
