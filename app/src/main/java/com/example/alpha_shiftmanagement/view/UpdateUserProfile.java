package com.example.alpha_shiftmanagement.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UpdateUserProfile extends AppCompatActivity {

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
        setContentView(R.layout.activity_update_user_profile);

        name = findViewById(R.id.btnSIname_uu);
        IDnumber = findViewById(R.id.btnSIIDnumber_uu);
        phoneNumber = findViewById(R.id.btnSIphoneNumber_uu);
        city = findViewById(R.id.btnSIcity_uu);
        email = findViewById(R.id.btnSIemail_uu);
        password = findViewById(R.id.btnSIpassword_uu);

        button = findViewById(R.id.btnSISignUp_uu);
        ShowProfile_SI =findViewById(R.id.ShowProfile_SI_uu);
        imageView = findViewById(R.id.ProfileImage_uu);
        progressBar = findViewById(R.id.progressbar_SI_uu);
        textTitle = findViewById(R.id.textTitle_uu);

        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile images");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updateprofile();
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

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void Updateprofile(){

        String Name = name.getText().toString();
        String iDnumber = IDnumber.getText().toString();
        String PhoneNumber = phoneNumber.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String City = city.getText().toString();

        if(imageUri != null){
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
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();   // getting uri from server
                                // [START transactions\

                                final DocumentReference sfDocRef = db.collection("user").document("profile");
                                storageReference = firebaseStorage.getInstance().getReference("profile images");

                                db.runTransaction(new Transaction.Function<Void>() {
                                    @Override
                                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                        DocumentSnapshot snapshot = transaction.get(sfDocRef);


                                        //double newPopulation = snapshot.getDouble("population") + 1;
                                        // transaction.update(sfDocRef, "population", newPopulation);

                                        transaction.update(sfDocRef, "Name", Name);
                                        transaction.update(sfDocRef, "IDnumber", iDnumber);
                                        transaction.update(sfDocRef, "phoneNumber", PhoneNumber);
                                        transaction.update(sfDocRef, "city", City);
                                        transaction.update(sfDocRef, "email", Email);
                                        transaction.update(sfDocRef, "password", Password);
                                        transaction.update(sfDocRef, "url", "url", downloadUri.toString());

                                        Intent intent = new Intent(UpdateUserProfile.this, ShowUserProfile.class);
                                        startActivity(intent);

                                        return null;
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.INVISIBLE);

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
                        }
                    });
        // else of image url is null or not

        } else {
            final DocumentReference sfDocRef = db.collection("user").document("profile");
            storageReference = firebaseStorage.getInstance().getReference("profile images");

            db.runTransaction(new Transaction.Function<Void>() {
                @Override
                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(sfDocRef);

                    // transaction.update(sfDocRef, "population", newPopulation);

                    transaction.update(sfDocRef, "Name", Name);
                    transaction.update(sfDocRef, "IDnumber", iDnumber);
                    transaction.update(sfDocRef, "phoneNumber", PhoneNumber);
                    transaction.update(sfDocRef, "city", City);
                    transaction.update(sfDocRef, "email", Email);
                    transaction.update(sfDocRef, "password", Password);


                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                            }
                        }




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
                            //String url = task.getResult().getString("url");

                            //Set all for edit
                           // Picasso.get().load(url).into(imageView);
                            name.setText(name_result);
                            IDnumber.setText(IDnumber_result);
                            phoneNumber.setText(phoneNumber_result);
                            city.setText(city_result);
                            email.setText(email_result);
                            password.setText(password_result);

                        }else {
                            Toast.makeText(UpdateUserProfile.this,"No Profile exist",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, ShowUserProfile.class);
        startActivity(intent);
    }
}