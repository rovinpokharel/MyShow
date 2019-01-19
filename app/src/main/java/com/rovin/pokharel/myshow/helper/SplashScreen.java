package com.rovin.pokharel.myshow.helper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.rovin.pokharel.myshow.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 20000);

        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        finish();
    }
}
