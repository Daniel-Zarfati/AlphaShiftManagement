package com.example.alpha_shiftmanagement.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.util.Event;
import com.example.alpha_shiftmanagement.fragment.NewMyAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NewManagerActivity extends AppCompatActivity {

    RecyclerView recview;
    NewMyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manager);

        recview = (RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Event"),Event.class)
                    .build();

        adapter=new NewMyAdapter(options);
        recview.setAdapter(adapter);


        }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }
        @Override
        protected void onStop () {
            super.onStop();
            adapter.stopListening();
        }
    }
