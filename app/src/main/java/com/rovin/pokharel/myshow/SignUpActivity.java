package com.rovin.pokharel.myshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rovin.pokharel.myshow.model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText userName, userEmail, userPassword, userConfirmPswd;
    private Button btnSignUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        userName = (EditText) findViewById(R.id.signup_username_et);
        userEmail = (EditText) findViewById(R.id.signup_useremail_et);
        userPassword = (EditText) findViewById(R.id.signup_userpassword_et);
        userConfirmPswd = (EditText) findViewById(R.id.signup_confirmpswd_et);
        btnSignUp = (Button) findViewById(R.id.signup_btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });
    }

    private void userSignUp(){
        final String fullname = userName.getText().toString();
        final String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPswd = userConfirmPswd.getText().toString();

        if (fullname.isEmpty()){
            userName.setError("enter full name");
            userName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            userEmail.setError("enter email");
            userEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            userPassword.setError("enter password");
            userPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPswd)){
            Toast.makeText(getApplicationContext(), "Password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //saving user data in database
                            String userID = firebaseAuth.getCurrentUser().getUid();
                            databaseReference = firebaseDatabase.getReference("users").child(userID);
                            User user = new User(userID, fullname, email);
                            databaseReference.setValue(user);

                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }
}
