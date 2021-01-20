package com.example.alpha_shiftmanagement.view;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class NewForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private ImageButton resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forgot_password);

        emailEditText = (EditText) findViewById(R.id.text_user_email);
        resetPasswordButton = (ImageButton) findViewById(R.id.btn_ResetPassword);
        progressBar = (ProgressBar) findViewById(R.id.PB_progressBar);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }

        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();  // trim if there are spaces

        if(email.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // if the user got the email
                if (task.isSuccessful()) {
                    Toast.makeText(NewForgotPasswordActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(NewForgotPasswordActivity.this, "Try again! Somthing wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
