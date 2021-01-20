package com.example.alpha_shiftmanagement.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ShowUserProfile extends AppCompatActivity {

    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageView;
    TextView nameEt,IDnumberEt,phoneNumberEt,cityEt,emailEt,passwordEt;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);

        floatingActionButton = findViewById(R.id.floatingbtn_sp);
        nameEt = findViewById(R.id.name_tv_sp);
        IDnumberEt = findViewById(R.id.IDnumber_tv_sp);
        phoneNumberEt = findViewById(R.id.Phonenumber_tv_sp);
        cityEt = findViewById(R.id.City_tv_sp);
        emailEt = findViewById(R.id.Email_tv_sp);
        passwordEt = findViewById(R.id.Password_tv_sp);

        imageView = findViewById(R.id.imageView_sp);
        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile images");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserProfile.this,UpdateUserProfile.class);
                startActivity(intent);
            }
        });

    }
    // onStart- when the activity is start it will check the data - if complete get it else Tost


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

                            //Set all
                            Picasso.get().load(url).into(imageView);
                            nameEt.setText(name_result);
                            IDnumberEt.setText(IDnumber_result);
                            phoneNumberEt.setText(phoneNumber_result);
                            cityEt.setText(city_result);
                            emailEt.setText(email_result);
                            passwordEt.setText(password_result);
                        }else {
                            Toast.makeText(ShowUserProfile.this,"No Profile exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void DeleteProfile(View view) {
        ShowDialog();
    }
    private void ShowDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowUserProfile.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                documentReference.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ShowUserProfile.this, "Profile deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
