package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NewRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ProfileImage;
    private TextView banner;
    private Button registerUser;
    private EditText Name,IdNumber,PhoneNumber,City,Email,Password;
    private ProgressBar PB_progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Name = (EditText) findViewById(R.id.ET_Name);
        IdNumber = (EditText) findViewById(R.id.ET_IDnumber);
        PhoneNumber = (EditText) findViewById(R.id.ET_phoneNumber);
        City = (EditText) findViewById(R.id.ET_city);
        Email = (EditText) findViewById(R.id.ET_email);
        Password = (EditText) findViewById(R.id.ET_password);

        PB_progressBar = (ProgressBar) findViewById(R.id.PB_progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String name = Name.getText().toString().trim();
        String Idnumber = IdNumber.getText().toString().trim();
        String phoneNumber = PhoneNumber.getText().toString().trim();
        String city = City.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(name.isEmpty()){
            Name.setError("Full Name is required!");
            Name.requestFocus();
            return;
        }
        if(Idnumber.isEmpty()){
            IdNumber.setError("ID number is required!");
            IdNumber.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty()){
            PhoneNumber.setError("Phone number is required!");
            PhoneNumber.requestFocus();
            return;
        }
        if(city.isEmpty()){
            City.setError("City is required!");
            City.requestFocus();
            return;
        }
        if(email.isEmpty()){
            Email.setError("Email address is required!");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password.setError("Password is required!");
            Password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please provide valid email!");
            Email.requestFocus();
            return;
        }
        if(password.length()<6){
            Password.setError("Min password length should be 6 characters!");
            Password.requestFocus();
            return;
        }

        PB_progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){  //if all the data from the user is vaild
                            User user = new User(name,Idnumber,phoneNumber,city,email,password);   // i need to try doing it with firestore

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())// return the id to the current user
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(NewRegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        PB_progressBar.setVisibility(View.INVISIBLE);

                                        startActivity(new Intent(NewRegisterActivity.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(NewRegisterActivity.this, "Failed to register, Try again!", Toast.LENGTH_LONG).show();
                                        PB_progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(NewRegisterActivity.this, "Failed to register, Try agin!", Toast.LENGTH_LONG).show();
                            PB_progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}