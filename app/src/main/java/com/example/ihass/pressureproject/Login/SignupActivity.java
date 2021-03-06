package com.example.ihass.pressureproject.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ihass.pressureproject.Classes.User;
import com.example.ihass.pressureproject.Implementation.MainView;
import com.example.ihass.pressureproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    // Authentication Instance
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Data Base Instance
    DatabaseReference data_refrence = FirebaseDatabase.getInstance().getReference();

    private Toast toast;

    private EditText NameText, AgeText, EmailText, MobileNumberText, PasswordText, ReEnterPasswordText;
    private Button SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SignUp");
        setContentView(R.layout.activity_signup);

        NameText = (EditText) findViewById(R.id.input_name);
        EmailText = (EditText) findViewById(R.id.input_email);
        PasswordText = (EditText) findViewById(R.id.input_password);
        ReEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        AgeText = (EditText) findViewById(R.id.input_age);
        MobileNumberText = (EditText) findViewById(R.id.input_mobile);
        SignUpButton = (Button) findViewById(R.id.btn_signup);

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


    public void LogInForm(View view) {
        Intent LogInIntent = new Intent(this, LoginActivity.class);
        startActivity(LogInIntent);
        finish();
    }

    public void SignUpUser(View view) {
        Log.d(TAG, "Sign-up");

        // Returns an error If there is no internet connection Or there is any invalid input
        if (!CheckInternetConnection() || !Validate()) {
            onSignUpFailed();
            return;
        }

        // Else
        SignUpButton.setEnabled(false);

        // TODO: Implement your own authentication logic here.
        CreateNewUser();

    }

    public boolean CheckInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //you are now connected to a network
            connected = true;
        } else {
            ShowToast("Connection Error!");
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setBackgroundColor(Color.RED);
            toastMessage.setTextColor(Color.WHITE);
            toast.show();
        }
        return connected;
    }

    public boolean Validate() {
        boolean valid = true;

        String name = NameText.getText().toString();
        String age = AgeText.getText().toString();
        String email = EmailText.getText().toString();
        String mobile = MobileNumberText.getText().toString();
        String password = PasswordText.getText().toString();
        String reEnterPassword = ReEnterPasswordText.getText().toString();

        // Name Text
        if (name.isEmpty() || name.length() < 3) {
            NameText.setError("at least 3 characters");
            valid = false;
        } else {
            NameText.setError(null);
        }

        // Age Text
        if (age.isEmpty() || Integer.parseInt(AgeText.getText().toString()) < 0 || Integer.parseInt(AgeText.getText().toString()) > 120) {
            AgeText.setError("Enter Valid Age");
            valid = false;
        } else {
            AgeText.setError(null);
        }

        // Email Text
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailText.setError("Enter Valid Email address");
            valid = false;
        } else {
            EmailText.setError(null);
        }

        // Mobile Number Text
        if (mobile.isEmpty() || mobile.length() != 11) {
            MobileNumberText.setError("Mobile Number is 11 characters");
            valid = false;
        } else {
            MobileNumberText.setError(null);
        }

        // Password Text
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            PasswordText.setError("Password must be from 4 to 10 alphanumeric characters");
            valid = false;
        } else {
            PasswordText.setError(null);
        }

        // ReEnter Password Text
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            ReEnterPasswordText.setError("Password Doesn't match");
            valid = false;
        } else {
            ReEnterPasswordText.setError(null);
        }

        return valid;
    }

    public void CreateNewUser() {

        final String name = NameText.getText().toString();
        final String email = EmailText.getText().toString();
        final String password = PasswordText.getText().toString();
        final int age = Integer.parseInt(AgeText.getText().toString());
        final int phoneNumber = Integer.parseInt(MobileNumberText.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // Creating a user in data base
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            ShowToast("Account Created Successfully");
                            // Pushing data to the user's ID
                            WriteDataToNewUser(FirebaseAuth.getInstance().getUid(), name, email, password, age, phoneNumber);

                            // OnSignUpSuccess
                            progressDialog.dismiss();
                            onSignUpSuccess();

                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            ShowToast("You are already registered");

                            // OnSignUpSuccess
                            progressDialog.dismiss();
                            onSignUpSuccess();

                        } else {
                            // If sign in fails, display a message to the user.
                            ShowToast(Objects.requireNonNull(task.getException()).getMessage());

                            // onSignUpFailed
                            progressDialog.dismiss();
                            onSignUpFailed();
                        }
                    }
                });
    }

    public void WriteDataToNewUser(String userId, String name, String email, String password, int age, int phoneNumber) {
        User user = new User(name, email, password, age, phoneNumber);

        // For Creating new user in (Accounts) table
        data_refrence.child("Accounts").child(userId).child("Personal Data").setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ShowToast("Data is added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error while restoring Data");
            }
        });
    }

    public void onSignUpFailed() {
        ShowToast("SignUp failed");
        SignUpButton.setEnabled(true);
    }

    public void onSignUpSuccess() {
        Intent FirstIntent = new Intent(SignupActivity.this, MainView.class);
        FirstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(FirstIntent);
        finish();
    }

}
