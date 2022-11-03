package com.mhike.m_hike.classes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.R;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList hikeName, hikeDescription, hikeDate;

    public HikeAdapter(Context context,
                       ArrayList hikeName,
                       ArrayList hikeDescription,
                       ArrayList hikeDate) {
        this.context = context;
        this.hikeName = hikeName;
        this.hikeDescription = hikeDescription;
        this.hikeDate = hikeDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hike_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.hikeName.setText(String.valueOf(hikeName.get(position)));
        holder.hikeDescription.setText(String.valueOf(hikeDescription.get(position)));
        holder.hikeDate.setText(String.valueOf(hikeDescription.get(position)));

    }

    @Override
    public int getItemCount() {
        return hikeName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName, hikeDescription, hikeDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.lblHikeName);
            hikeDescription = itemView.findViewById(R.id.lblHikeDescription);
            hikeDate = itemView.findViewById(R.id.lblHikeDate);
        }
    }
}
