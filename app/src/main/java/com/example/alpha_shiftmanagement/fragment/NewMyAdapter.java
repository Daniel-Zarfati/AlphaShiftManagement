package com.example.alpha_shiftmanagement.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_shiftmanagement.R;
import com.example.alpha_shiftmanagement.util.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewMyAdapter extends FirebaseRecyclerAdapter<Event,NewMyAdapter.myviewholder> {


    public NewMyAdapter(@NonNull FirebaseRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Event Event) {
        holder.tv_Date.setText(Event.getDate());
        holder.tv_TakingPlace.setText(Event.getTakingPlace());
        holder.tv_Availability.setText(Event.getAvailability());

        //Glide.with(holder.img1.getContext()).load(Event.getTakingPlace()).into(holder.tv_TakingPlace);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView tv_TakingPlace,tv_Date, tv_Availability;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1= (CircleImageView)itemView.findViewById(R.id.TakingPlace_img);
            tv_TakingPlace=itemView.findViewById(R.id.Et_TakingPlace);
            tv_Date=itemView.findViewById(R.id.Et_Date);
            tv_Availability =itemView.findViewById(R.id.Et_Availability);

        }
    }
}
