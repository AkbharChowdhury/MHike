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
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.models.Observation;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Observation> observationList;
    private Activity activity;
    private final ActivityForm activityForm;


    public ObservationAdapter(ArrayList<Observation> observationList, Activity activity, Context context, ActivityForm activityForm){
        this.activity = activity;
        this.context = context;
        this.activityForm = activityForm;
        this.observationList = observationList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Observation observation = observationList.get(position);
        // Note: the hike ID MUST be in String format to set the next
        holder.observationID.setText(String.valueOf(observation.getObservationID()));
        holder.observation.setText(observation.getObservation());
        holder.observationDate.setText(Helper.formatDateShort(observation.getObservationDate()));
        holder.observationTime.setText(" "+Helper.formatTime(observation.getObservationTime()));
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }

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
