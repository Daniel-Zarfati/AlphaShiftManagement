package com.example.alpha_shiftmanagement.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CreateUserProfile extends AppCompatActivity {

    EditText name, IDnumber, phoneNumber, city, email, password;
    Button button,ShowProfile_SI;
    ProgressBar progressBar;
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageView;
    TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_profile);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.btnSIname);
        IDnumber = findViewById(R.id.btnSIIDnumber);
        phoneNumber = findViewById(R.id.btnSIphoneNumber);
        city = findViewById(R.id.btnSIcity);
        email = findViewById(R.id.btnSIemail);
        password = findViewById(R.id.btnSIpassword);

        button = findViewById(R.id.btnSISignUp);
        //ShowProfile_SI =findViewById(R.id.ShowProfile_SI);
        imageView = findViewById(R.id.ProfileImage);
        progressBar = findViewById(R.id.progressbar_SI);
        textTitle = findViewById(R.id.textTitle);

        //NewUser user1 = new NewUser(name.toString(),IDnumber.toString(),phoneNumber.toString(),city.toString(),email.toString(),password.toString());
        //CollectionReference ref = db.collection("Users");

        documentReference = db.collection("All Users").document("profile");
        //documentReference = db.collection("All Users").document("profile");
        storageReference = FirebaseStorage.getInstance().getReference("profile images");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData();
            }
        });

    }

    public void ChooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null) {
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(imageView);
        }

    }

        // will detect the extention of the file of the image

        private String getFileExt(Uri uri){
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }

        //Upload the file in the FireBaseServer
        public void UploadData() {

            String Name = name.getText().toString();
            String iDnumber = IDnumber.getText().toString();
            String PhoneNumber = phoneNumber.getText().toString();
            String Email = email.getText().toString();
            String Password = password.getText().toString();
            String City = city.getText().toString();


//            NewUser user1 = new NewUser(name,IDnumber,phoneNumber,city,email,password);
//            CollectionReference ref = db.collection("Users");


            if(!TextUtils.isEmpty(Name) || !TextUtils.isEmpty(iDnumber) || !TextUtils.isEmpty(PhoneNumber) || !TextUtils.isEmpty(Email) || !TextUtils.isEmpty(Password) || !TextUtils.isEmpty(City)|| imageUri !=null){

                progressBar.setVisibility(View.VISIBLE);
                final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
                uploadTask = reference.putFile(imageUri);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        return reference.getDownloadUrl();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){
                                    Uri downloadUri = task.getResult();   // getting uri from server

                                    Map<String,String> profile = new HashMap<>();

                                    profile.put("Name", Name);
                                    profile.put("IDnumber", iDnumber);
                                    profile.put("phoneNumber", PhoneNumber);
                                    profile.put("city", City);
                                    profile.put("email", Email);
                                    profile.put("password", Password);
                                    profile.put("url",downloadUri.toString());


//                                    documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//                                            Toast.makeText(CreateUserProfile.this, "User1 added", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//                                    }
//                                    ref.add(user1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//                                            Toast.makeText(CreateUserProfile.this, "User1 added", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });


                                    documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(CreateUserProfile.this, "Profile Created", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(CreateUserProfile.this, ShowUserProfile.class);
                                            startActivity(intent);

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                }

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateUserProfile.this, "failed",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateUserProfile.this, "failed",Toast.LENGTH_SHORT).show();
                            }
                        });

            }else{
                Toast.makeText(this,"All Fields required",Toast.LENGTH_SHORT).show();
            }


        }


        //Edit Data!

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String name_result = task.getResult().getString("Name");
                            String IDnumber_result = task.getResult().getString("IDnumber");
                            String phoneNumber_result = task.getResult().getString("phoneNumber");
                            String city_result = task.getResult().getString("city");
                            String email_result = task.getResult().getString("email");
                            String password_result = task.getResult().getString("password");
                            String url = task.getResult().getString("url");

                            //Set all for edit
                            Picasso.get().load(url).into(imageView);
                            name.setText(name_result);
                            IDnumber.setText(IDnumber_result);
                            phoneNumber.setText(phoneNumber_result);
                            city.setText(city_result);
                            email.setText(email_result);
                            password.setText(password_result);

                        }else {
                            Toast.makeText(CreateUserProfile.this,"No Profile exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public void ShowProfile(View view) {

    }


}