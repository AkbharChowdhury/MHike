package com.mhike.m_hike.classes;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.EditHikeActivity;
import com.mhike.m_hike.MainActivity;
import com.mhike.m_hike.R;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> {
    private Context context;
    private ArrayList observation;
    private ArrayList observationDateTime;
    private ArrayList obervationID;


    private Activity activity;

    public ObservationAdapter(
            Activity activity,
            Context context,
            ArrayList observationID,
            ArrayList observation,
            ArrayList observationDateTime
            ) {
        this.activity = activity;
        this.context = context;
        this.obervationID = observationID;
        this.observation = observation;
        this.observationDateTime = observationDateTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_row, parent, false);
        return new MyViewHolder(view);



    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.observationID.setText(String.valueOf(obervationID.get(position)));
        holder.observation.setText(String.valueOf(observation.get(position)));
        holder.dateTime.setText(String.valueOf(observationDateTime.get(position)));
        holder.mainLayoutObservation.setOnClickListener(view -> activity.startActivityForResult(ObservationIntent(position), 1));

    }

    @Override
    public int getItemCount() {
        return obervationID.size();
    }

    private Intent ObservationIntent(int position){
        Intent intent = new Intent(context, EditHikeActivity.class);
        intent.putExtra("observationID", String.valueOf(obervationID.get(position)));
        return intent;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mainLayoutObservation;
        TextView observationID;
        TextView observation;
        TextView dateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            observationID = itemView.findViewById(R.id.txtObservationID);
            observation = itemView.findViewById(R.id.txtObservation);
            dateTime = itemView.findViewById(R.id.txtDateTime);
            mainLayoutObservation = itemView.findViewById(R.id.mainLayoutObservation);

        }
    }
}
