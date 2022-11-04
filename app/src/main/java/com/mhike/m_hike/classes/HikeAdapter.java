package com.mhike.m_hike.classes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.HikeActivity;
import com.mhike.m_hike.R;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList hikeName, hikeDescription, hikeDate;
    private ArrayList hikeID;


    private LinearLayout mainLayout;
    private Activity activity;



    public HikeAdapter(Context context,
                       ArrayList hikeID,
                       ArrayList hikeName,
                       ArrayList hikeDescription,
                       ArrayList hikeDate) {
        this.context = context;
        this.hikeID = hikeID;
        this.hikeName = hikeName;
        this.hikeDescription = hikeDescription;
        this.hikeDate = hikeDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hike_row, parent, false);
        return new MyViewHolder(view);
//        View v = LayoutInflater.from(context).inflate(R.layout.hike_row, parent, false);
//        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.hikeName.setText(String.valueOf(hikeName.get(position)));
        holder.hikeDescription.setText(String.valueOf(hikeDescription.get(position)));
        holder.hikeDate.setText(String.valueOf(hikeDescription.get(position)));
        holder.mainLayout.setOnClickListener(view -> activity.startActivityForResult(HikeIntent(position), 1));

    }

    private Intent HikeIntent(int position){
        Intent intent = new Intent(context, HikeActivity.class);
        intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
        return intent;
    }

    @Override
    public int getItemCount() {
        return hikeName.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName, hikeDescription, hikeDate;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.lblHikeName);
            hikeDescription = itemView.findViewById(R.id.lblHikeDescription);
            hikeDate = itemView.findViewById(R.id.lblHikeDate);
        }
    }



}
