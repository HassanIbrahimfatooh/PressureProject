package com.example.ihass.pressureproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.ihass.pressureproject.Implementation.MainView;
import com.example.ihass.pressureproject.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main");
        setContentView(R.layout.activity_main);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            //Not signed in, launch the Sign In Activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            //Signed in, launch the Main View Activity
            startActivity(new Intent(this, MainView.class));
            finish();
        }

    }
}
