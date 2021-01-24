package com.example.alpha_shiftmanagement.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.fragment.SecondActivity;
import com.example.alpha_shiftmanagement.util.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {


    //private TextView banner;
    private Button uploadEvent;
    private EditText namePlace, date, availability, startHour, endHour, salary;

    private ProgressBar PB_progressBar;

    ImageView TakingPlace_img;
    Uri filepath;
    Button selectImage;

    Bitmap bitmap;

    DatabaseReference myRef;
    Event event;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference().child("Events");


        event = new Event();




        uploadEvent = findViewById(R.id.btn_uploadEvent);
        uploadEvent.setOnClickListener(this);   // send all data to firebase

        namePlace = (EditText) findViewById(R.id.Et_TakingPlace);
        date = (EditText) findViewById(R.id.Et_Date);
        availability = (EditText) findViewById(R.id.Et_Availability);
        startHour = (EditText) findViewById(R.id.Et_StartHour);
        endHour = (EditText) findViewById(R.id.Et_EndHour);
        salary = (EditText) findViewById(R.id.Et_EventSalary);

        PB_progressBar = (ProgressBar) findViewById(R.id.PB_progressBar);



        //banner = (TextView) findViewById(R.id.banner);
        //banner.setOnClickListener(this);

        TakingPlace_img = (ImageView)findViewById(R.id.TakingPlace_img);
        selectImage = (Button) findViewById(R.id.btn_select_Imgae);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(CreateEventActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath = data.getData();
            try{  // display the image
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                TakingPlace_img.setImageBitmap(bitmap);

            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.banner:
//                startActivity(new Intent(this,MainActivity.class));
//                break;
              case R.id.btn_uploadEvent:
                  registerEvent();
                  break;

        }
    }

    private void registerEvent() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploding");
        dialog.show();


        String namePlace = this.namePlace.getText().toString().trim();
        String date = this.date.getText().toString().trim();
        String availability = this.availability.getText().toString().trim();
        String startHour = this.startHour.getText().toString().trim();
        String endHour = this.endHour.getText().toString().trim();
        String salary = this.salary.getText().toString().trim();


        event = new Event(namePlace,date,startHour,endHour,availability,salary);
        myRef = FirebaseDatabase.getInstance().getReference().child("Events");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploder = storage.getReference("Image1" + new Random().nextInt(50));

        uploder.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :" +(int)percent+" %");
                    }
                });

        if(namePlace.isEmpty()){
            this.namePlace.setError("Full Name is required!");
            this.namePlace.requestFocus();
            return;
        }
        if(date.isEmpty()){
            this.date.setError("Date is required!");
            this.date.requestFocus();
            return;
        }
        if(availability.isEmpty()){
            this.availability.setError("Availability number is required!");
            this.availability.requestFocus();
            return;
        }
        if(startHour.isEmpty()){
            this.startHour.setError("startHour is required!");
            this.startHour.requestFocus();
            return;
        }
        if(endHour.isEmpty()){
            this.endHour.setError("End hour  is required!");
            this.endHour.requestFocus();
            return;
        }
        if(salary.isEmpty()){
            this.salary.setError("Salary is required!");
            this.salary.requestFocus();
            return;
        }

        PB_progressBar.setVisibility(View.VISIBLE);

        myRef.push().setValue(event);

        startActivity(new Intent(CreateEventActivity.this, SecondActivity.class));


//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if(task.isSuccessful()){  //if all the data from the user is vaild
                            //Event event = new Event(namePlace,date,availability,startHour,endHour,salary);
//                            User user = new User(namePlace,date,Availability,startHour,email,password);   // i need to try doing it with firestore
//


//                            FirebaseDatabase.getInstance().getReference("Events")
//
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())// return the id to the current user
//                                    .setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(CreateEventActivity.this, "Event has been registered successfully!", Toast.LENGTH_LONG).show();
//                                        PB_progressBar.setVisibility(View.INVISIBLE);
//
//                                        startActivity(new Intent(CreateEventActivity.this, SecondActivity.class));
//                                    }else{
//                                        Toast.makeText(CreateEventActivity.this, "Failed to register the Event, Try again!", Toast.LENGTH_LONG).show();
//                                        PB_progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
    }
}