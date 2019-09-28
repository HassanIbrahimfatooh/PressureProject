package com.example.ihass.pressureproject.Implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.Time;
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
import java.util.Calendar;
import java.util.Objects;

public class MeasureActivity extends AppCompatActivity {

    private Toast toast;

    // Database instances
    private DatabaseReference data_refrence = FirebaseDatabase.getInstance().getReference();

    // Local EditTexts
    EditText UpperMeasureText, LowerMeasureText, DayText, TimeText;

    // Date Variables
    Calendar calander = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        // Writing Date into text
        DayText.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calander.getTime()));

        // Writing Time into text
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        TimeText.setText(String.valueOf(today.format("%k:%M:%S")));
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
            String Time = String.valueOf(TimeText.getText());

            // Creating measure instance
            Measurement measurement = new Measurement(upperMeasure, lowerMeasure, Day, Time);

            // Getting UserID
            String UserID = Objects.requireNonNull(FirebaseAuth.getInstance().getUid());

            // adding it into database
            data_refrence.child("Accounts").child(UserID).child("Measurements").push().setValue(measurement).addOnSuccessListener(new OnSuccessListener<Void>() {
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

