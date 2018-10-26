package com.example.ihass.pressureproject.Implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ihass.pressureproject.R;

public class MainView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main View");
        setContentView(R.layout.activity_main_view);
    }
}
