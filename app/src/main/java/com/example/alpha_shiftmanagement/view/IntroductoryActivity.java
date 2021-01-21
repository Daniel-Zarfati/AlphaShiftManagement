package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.alpha_shiftmanagement.R;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView splashImag;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        splashImag = findViewById(R.id.image);
        lottieAnimationView =  findViewById(R.id.lottie);

        //splashImag.animate().translationY(-1600).setDuration(1000).setStartDelay(1800); // 1000 equels 1 sec
        lottieAnimationView.animate().translationY(-1400).setDuration(1000).setStartDelay(1800); // 1000 equels 1 sec

       // splashImag.animate().translationY(-1600).setDuration(1000).setStartDelay(5000); // 1000 equels 1 sec
      //  lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(5000); // 1000 equels 1 sec


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }, 2350);

    }


}