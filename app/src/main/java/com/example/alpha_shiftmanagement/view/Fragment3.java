package com.example.alpha_shiftmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alpha_shiftmanagement.R;

public class Fragment3 extends Fragment {
    private static final String TAG = "Fragment3";

    private Button btnNavFrag1;
    private Button btnNavFrag2;
    private Button btnNavFrag3;
    private Button btnNavThirdActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container,false);

        btnNavFrag1 = (Button) view.findViewById(R.id.btnNavFrag1);
        btnNavFrag2 = (Button) view.findViewById(R.id.btnNavFrag2);
        btnNavFrag3 = (Button) view.findViewById(R.id.btnNavFrag3);
        btnNavThirdActivity = (Button)view.findViewById(R.id.btnNavThirdActivity);

        btnNavFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Going to Fragment 1", Toast.LENGTH_SHORT).show();

                ((SecondActivity)getActivity()).setmViewPager(0); // Accsess to all secondactivity methods
            }
        });
        btnNavFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Going to Fragment 2", Toast.LENGTH_SHORT).show();
                ((SecondActivity)getActivity()).setmViewPager(1); // Accsess to all secondactivity methods
            }
        });
        btnNavFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Going to Fragment 3", Toast.LENGTH_SHORT).show();
                ((SecondActivity)getActivity()).setmViewPager(2); // Accsess to all secondactivity methods
            }
        });
        btnNavThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Going to Third Activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ThirdActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}
