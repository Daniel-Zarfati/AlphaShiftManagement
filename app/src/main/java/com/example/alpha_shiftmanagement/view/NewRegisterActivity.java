package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    private TextView banner;
    private Button registerUser;
    private EditText Name,IdNumber,PhoneNumber,City,Email,Password;
    private ProgressBar PB_progressBar;

//    private ImageView ImageView;
//    private Uri imageUri;
//    private static final int PICK_IMAGE = 1;
//    FirebaseStorage firebaseStorage;
//    //FirebaseAuth mAuth;
//    StorageReference storageReference;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    DocumentReference documentReference;
//    UploadTask uploadTask;



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

//        ImageView = (ImageView) findViewById(R.id.ProfileImage);
//        documentReference = db.collection("All Users").document("profile");
//        storageReference = FirebaseStorage.getInstance().getReference("profile images");


    }


//    public void ChooseImageProfile(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null) {
//            imageUri = data.getData();
//
//            Picasso.get().load(imageUri).into(ImageView);
//        }
//
//    }
//    private String getFileExt(Uri uri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//    }




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



//                        final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
//                        uploadTask = reference.putFile(imageUri);
//                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//                                return reference.getDownloadUrl();
//                            }
//                        })
//                                .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        if (task.isSuccessful()) {
//                                            Uri downloadUri = task.getResult();   // getting uri from server
//
//                                            Map<String, String> profile = new HashMap<>();
//
//                                            profile.put("Name",Name.getText().toString());
//                                            profile.put("ID number",IdNumber.getText().toString());
//                                            profile.put("Phone Number",PhoneNumber.getText().toString());
//                                            profile.put("Email",Email.getText().toString());
//                                            profile.put("Password",Password.getText().toString());
//                                            profile.put("url",downloadUri.toString());
//
//
//
//
//                                            documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//
//
//                                                    PB_progressBar.setVisibility(View.INVISIBLE);
//                                                    Toast.makeText(NewRegisterActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
//
//                                                    Intent intent = new Intent(NewRegisterActivity.this, ShowUserProfile.class);
//                                                    startActivity(intent);
//
//                                                }
//                                            })
//                                                    .addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//
//                                                        }
//                                                    });
//                                        }
//
//                                    }
//
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(NewRegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                });




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