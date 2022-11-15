package com.mhike.m_hike.classes.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> {
    private Context context;
    private ArrayList observation;
    private ArrayList observationDate;
    private ArrayList observationTime;

    private ArrayList observationID;


    private Activity activity;

    public ObservationAdapter(
            Activity activity,
            Context context,
            ArrayList observationID,
            ArrayList observation,
            ArrayList observationDate,
            ArrayList observationTime

    ) {
        this.activity = activity;
        this.context = context;
        this.observationID = observationID;
        this.observation = observation;
        this.observationDate = observationDate;
        this.observationTime = observationTime;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_row, parent, false);
        return new MyViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.observationID.setText(String.valueOf(observationID.get(position)));
        holder.observation.setText(String.valueOf(observation.get(position)));
        holder.observationDate.setText(Helper.formatDateShort(String.valueOf(observationDate.get(position))));
        holder.observationTime.setText("  " + Helper.formatTime(String.valueOf(observationTime.get(position))));


//        holder.mainLayoutObservation.setOnClickListener(view -> activity.startActivityForResult(ObservationIntent(position), 1));

    }

    @Override
    public int getItemCount() {
        return observationID.size();
    }
//
//    private Intent ObservationIntent(int position){
//        Intent intent = new Intent(context, ViewHikeObservationActivity.class);
//        intent.putExtra("observationID", String.valueOf(observationID.get(position)));
//        return intent;
//    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mainLayoutObservation;
        TextView observationID;
        TextView observation;
        TextView observationDate;
        TextView observationTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            observationID = itemView.findViewById(R.id.txtObservationID);
            observation = itemView.findViewById(R.id.txtObservation);
            observationDate = itemView.findViewById(R.id.txtObservationDate);
            observationTime = itemView.findViewById(R.id.txtObservationTime);
            mainLayoutObservation = itemView.findViewById(R.id.mainLayoutObservation);

        }
    }
}
