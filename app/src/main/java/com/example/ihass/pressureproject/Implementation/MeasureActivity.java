package com.example.ihass.pressureproject.Implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ihass.pressureproject.Classes.Measurement;
import com.example.ihass.pressureproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MeasureActivity extends AppCompatActivity {

    // Database instances
    private DatabaseReference data_refrence = FirebaseDatabase.getInstance().getReference();

    // Local EditTexts
    EditText UpperMeasureText, LowerMeasureText, DayText, TimeText;

    // Date Variables
    Calendar calander = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat;

    private Toast toast;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Measure");
        setContentView(R.layout.activity_measure);

        // Declaring all EditText forms
        UpperMeasureText = (EditText) findViewById(R.id.upper_measure);
        LowerMeasureText = (EditText) findViewById(R.id.lower_measure);
        DayText = (EditText) findViewById(R.id.Day);
        TimeText = (EditText) findViewById(R.id.Time);

        // Date format
        simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");

        // Writing Date into text
        DayText.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calander.getTime()));

        // Writing Time into text
        TimeText.setText(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
    }

    @SuppressLint("ShowToast")
    public void ShowToast(String message) {
        try {
            toast.getView().isShown();
            toast.setText(message);
        } catch (Exception e) {
            toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public void CreateNewMeasure(View view) {

        if (UpperMeasureText.getText().toString().equals("") || LowerMeasureText.getText().toString().equals("") || DayText.getText().toString().equals("") || TimeText.getText().toString().equals("")) {
            // Showing Failure Message
            ShowToast("Fill necessary inputs");
        } else {
            int upperMeasure = Integer.parseInt(UpperMeasureText.getText().toString());
            int lowerMeasure = Integer.parseInt(LowerMeasureText.getText().toString());
            String Day = DayText.getText().toString();
            int Time = Integer.parseInt(String.valueOf(TimeText.getText()));

            // Creating measure instance
            Measurement measurement = new Measurement(upperMeasure, lowerMeasure, Day, Time);

            // Getting UserID
            String UserID = Objects.requireNonNull(FirebaseAuth.getInstance().getUid());

            // adding it into database
            data_refrence.child("Accounts").child(UserID).child("Measurements").setValue(measurement).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Showing Success Message
                    ShowToast("Measure Created Successfully");
                    Intent MainActivityIntent = new Intent(MeasureActivity.this, MainView.class);
                    startActivity(MainActivityIntent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ShowToast("An Error has occurred");
                }
            });
        }
    }
}

