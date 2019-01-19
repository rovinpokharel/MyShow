package com.rovin.pokharel.myshow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rovin on 11/13/2018.
 */

public class BookingActivity extends AppCompatActivity {

    private ImageView movieBanner;
    private CheckBox a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_seat);

        movieBanner = (ImageView) findViewById(R.id.booking_movie);
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra(ConstantData.MOVIE_IMAGE)).into(movieBanner);

        a1 = (CheckBox) findViewById(R.id.a1);
        a2 = (CheckBox) findViewById(R.id.a2);
        a3 = (CheckBox) findViewById(R.id.a3);
        a4 = (CheckBox) findViewById(R.id.a4);
        b1 = (CheckBox) findViewById(R.id.b1);
        b2 = (CheckBox) findViewById(R.id.b2);
        b3 = (CheckBox) findViewById(R.id.b3);
        b4 = (CheckBox) findViewById(R.id.b4);
        c1 = (CheckBox) findViewById(R.id.c1);
        c2 = (CheckBox) findViewById(R.id.c2);
        c3 = (CheckBox) findViewById(R.id.c3);
        c4 = (CheckBox) findViewById(R.id.c4);
        d1 = (CheckBox) findViewById(R.id.d1);
        d2 = (CheckBox) findViewById(R.id.d2);
        d3 = (CheckBox) findViewById(R.id.d3);
        d4 = (CheckBox) findViewById(R.id.d4);

    }
}
