package com.example.ihass.pressureproject.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ihass.pressureproject.Implementation.MainView;
import com.example.ihass.pressureproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText EmailText, PasswordText;
    private Button LogInButton;

    // Fire base Authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LogIn");
        setContentView(R.layout.activity_login);

        EmailText = (EditText) findViewById(R.id.input_email);
        PasswordText = (EditText) findViewById(R.id.input_password);
        LogInButton = (Button) findViewById(R.id.btn_login);

    }

    public void SignUpForm(View view) {
        Intent SignUpIntent = new Intent(this, SignupActivity.class);
        startActivity(SignUpIntent);
        finish();
    }

    public void LogIn(View view) {
        Log.d(TAG, "Login");

        if (!Validate()) {
            onLoginFailed();
            return;
        }

        LogInButton.setEnabled(false);

        // TODO: Implement your own authentication logic here.
        LogInUser();
    }

    private void LogInUser() {
        String email = EmailText.getText().toString();
        String password = PasswordText.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Authenticated Successfully");

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;

                            Toast.makeText(getApplicationContext(), "Authenticated Successfully", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();

                            // onLoginSucccess();
                            onLoginSuccess();
                            progressDialog.dismiss();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Authentication Failure", task.getException());
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();

                            // onLoginFailed();
                            onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private boolean Validate() {
        boolean valid = true;

        String email = EmailText.getText().toString();
        String password = PasswordText.getText().toString();

        // Email Text
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailText.setError("Enter Valid Email address");
            valid = false;
        } else {
            EmailText.setError(null);
        }

        // Password Text
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            PasswordText.setError("Password must be from 4 to 10 alphanumeric characters");
            valid = false;
        } else {
            PasswordText.setError(null);
        }

        return valid;
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "LogIn failed", Toast.LENGTH_LONG).show();
        LogInButton.setEnabled(true);
    }

    public void onLoginSuccess() {
        Intent FirstIntent = new Intent(LoginActivity.this, MainView.class);
        FirstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(FirstIntent);
        finish();
    }

    public void ForgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
        finish();
    }
}