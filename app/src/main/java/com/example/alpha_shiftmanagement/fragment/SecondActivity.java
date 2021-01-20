package com.example.alpha_shiftmanagement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.util.User;
import com.example.alpha_shiftmanagement.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {  // Profile & Bar

    public static final String TAG = "SecondActivity";

    private FirebaseUser user;
    private DatabaseReference DBreference;
    private String userID;

    private Button logout;
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG,"onCreate: Started.");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        //setup the pager
        setupViewPager(mViewPager);

        logout = (Button) findViewById(R.id.SignOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        DBreference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        // final because itn inner classes
        final TextView greetingTextView = (TextView) findViewById(R.id.TV_greetings);

        DBreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String Name = userProfile.Name;

                    greetingTextView.setText("Welcome "+Name+ ",");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SecondActivity.this, "Profile fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(),"Fragment1-ChangeName");
        adapter.addFragment(new Fragment2(),"Fragment2-ChangeName");  // i can change to order !!!
        adapter.addFragment(new Fragment3(),"Fragment3-ChangeName");
        viewPager.setAdapter(adapter);
    }

    public void setmViewPager(int fragmentNumber){    // move threw fragments
        mViewPager.setCurrentItem(fragmentNumber);
    }


}