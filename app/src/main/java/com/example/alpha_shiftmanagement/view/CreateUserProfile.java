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
import com.google.firebase.firestore.DocumentReference;
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
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageView;
    TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_profile);

        name = findViewById(R.id.btnSIname);
        IDnumber = findViewById(R.id.btnSIIDnumber);
        phoneNumber = findViewById(R.id.btnSIphoneNumber);
        city = findViewById(R.id.btnSIcity);
        email = findViewById(R.id.btnSIemail);
        password = findViewById(R.id.btnSIpassword);

        button = findViewById(R.id.btnSISignUp);
        ShowProfile_SI =findViewById(R.id.ShowProfile_SI);
        imageView = findViewById(R.id.ProfileImage);
        progressBar = findViewById(R.id.progressbar_SI);
        textTitle = findViewById(R.id.textTitle);

        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile images");

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



    public void ShowProfile(View view) {

    }


}