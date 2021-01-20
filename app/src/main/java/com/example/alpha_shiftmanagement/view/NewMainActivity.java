package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_shiftmanagement.R;

public class NewMainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        register = (TextView) findViewById(R.id.btn_SignUp);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_SignUp:
                startActivity(new Intent(this, NewRegisterActivity.class));
        }
    }
}