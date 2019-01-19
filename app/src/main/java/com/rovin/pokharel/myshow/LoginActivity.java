package com.rovin.pokharel.myshow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rovin.pokharel.myshow.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private Button btnLogin;
    private TextView btnSignup;

    // firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        userEmail = (EditText) findViewById(R.id.login_username_et);
        userPassword = (EditText) findViewById(R.id.login_password_et);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnSignup = (TextView) findViewById(R.id.login_tv_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (new SessionManager(getApplicationContext()).isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, Dashboard.class));
            finish();
        }

    }

    private void logInUser(){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.isEmpty()){
            userEmail.setError("enter email");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            userPassword.setError("enter password");
            userPassword.requestFocus();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        new SessionManager(getApplicationContext()).setLogin(true);
                        startActivity(new Intent(LoginActivity.this, Dashboard.class));
                        finish();
                    }
                }
                );
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Incorrect Username or Password!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signUpUser(){
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
