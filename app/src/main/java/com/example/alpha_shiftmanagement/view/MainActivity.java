package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.fragment.SecondActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView register, forgotPassword;
    private EditText Et_Email, Et_Password;
    //private ImageButton SignIn;
    private ImageView loginBtn;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     register = (TextView) findViewById(R.id.btn_SignUp);
     register.setOnClickListener(this);

//     SignIn = (ImageButton) findViewById(R.id.btn_SignIn);
//     SignIn.setOnClickListener(this);

     Et_Email = (EditText) findViewById(R.id.text_user_email);
     Et_Password = (EditText) findViewById(R.id.text_user_password);

     progressBar = (ProgressBar) findViewById(R.id.PB_progressBar);

     forgotPassword = (TextView) findViewById(R.id.forgotPassword);
     forgotPassword.setOnClickListener(this);

     mAuth = FirebaseAuth.getInstance();

     loginBtn = (ImageView) findViewById(R.id.loginBtn2);
     loginBtn.setOnClickListener(this);




    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SignUp:
                startActivity(new Intent(this,NewRegisterActivity.class));
                break;

//            case R.id.btn_SignIn:
//                userLogin();
//                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,NewForgotPasswordActivity.class));
                break;

            case R.id.loginBtn2:
                userLogin();
                break;

            case R.id.Security:
                startActivity(new Intent(this,ManagerLogin.class));



        }

    }

    private void userLogin() {
        String email = Et_Email.getText().toString().trim();
        String password = Et_Password.getText().toString().trim();

        if(email.isEmpty()){
            Et_Email.setError("Email is required!");
            Et_Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Et_Email.setError("Please provide valid email!");
            Et_Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Et_Password.setError("Password is required");
            Et_Password.requestFocus();
            return;
        }
        if(password.length()<6){
            Et_Password.setError("Min password length should be 6 characters!");
            Et_Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your acount!",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




//    public static final String TAG = "MainActivity";
//
//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mAuth = FirebaseAuth.getInstance();
//
//
//    }
//
//
//    public void SignIn(View view){
//
//        TextView tE = findViewById(R.id.text_user_email);
//        String email = tE.getText().toString();
//        TextView tP = findViewById(R.id.text_user_password);
//        String password = tP.getText().toString();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Authentication Success.",
//                                    Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//
//                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                            startActivity(intent);
//
//                        } else {
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//
//
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//
//    private void updateUI(FirebaseUser currentUser) {}
//
//    public void movePage(View view) {
//            Intent intent = new Intent(this, CreateUserProfile.class);
//            startActivity(intent);
//    }
//
////    public void showProfile(View view) {
////        Intent intent = new Intent(this, CreateUserProfile.class);
////        startActivity(intent);
////    }
//

}