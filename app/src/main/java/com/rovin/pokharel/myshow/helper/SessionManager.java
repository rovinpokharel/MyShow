package com.rovin.pokharel.myshow.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rovin on 8/6/2018.
 */

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyShow";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    //setting the session after login and logout of users
    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "UserData login session modified!");
    }
    // to check the session of the users
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public boolean isAdmin(){
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().equals("rovinpokharel123@gmail.com")){
            return true;
        }else {
            return false;
        }
    }
}
