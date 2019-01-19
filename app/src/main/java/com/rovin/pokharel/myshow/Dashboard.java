package com.rovin.pokharel.myshow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.rovin.pokharel.myshow.helper.BottomNavigationViewHelper;
import com.rovin.pokharel.myshow.helper.SessionManager;
import com.rovin.pokharel.myshow.homefrags.AboutUsFragment;
import com.rovin.pokharel.myshow.homefrags.ContactUsFragment;
import com.rovin.pokharel.myshow.homefrags.HelpFragment;
import com.rovin.pokharel.myshow.homefrags.HomeFragment;

public class Dashboard extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private FloatingActionButton fab;

    private FirebaseAuth firebaseAuth;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(getApplicationContext());

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        if (sessionManager.isAdmin()){
            fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Dashboard.this, AddMovieActivity.class));
                }
            });
        }
        fragmentManager = getSupportFragmentManager();

        bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);
        fragment = new HomeFragment();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_aboutus:
                        fragment = new AboutUsFragment();
                        break;
                    case R.id.nav_contactus:
                        fragment = new ContactUsFragment();
                        break;
                    case R.id.nav_help:
                        fragment = new HelpFragment();
                        break;
                }

                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.setLogin(false);
                startActivity(new Intent(Dashboard.this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
}
