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

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList hikeName;
    private ArrayList hikeDescription;
    private ArrayList hikeDate;
    private ArrayList hikeID;
    private Activity activity;

    public HikeAdapter(
            Activity activity,
            Context context,
                       ArrayList hikeID,
                       ArrayList hikeName,
                       ArrayList hikeDescription,
                       ArrayList hikeDate) {
        this.activity = activity;
        this.context = context;
        this.hikeID = hikeID;
        this.hikeName = hikeName;
        this.hikeDescription = hikeDescription;
        this.hikeDate = hikeDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hike_row, parent, false);
        return new MyViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.hikeID.setText(String.valueOf(hikeID.get(position)));
        holder.hikeName.setText(String.valueOf(hikeName.get(position)));
        holder.hikeDescription.setText(String.valueOf(hikeDescription.get(position)));
        holder.hikeDate.setText(String.valueOf(hikeDate.get(position)));
        holder.mainLayout.setOnClickListener(view -> activity.startActivityForResult(hikeIntent(position), 1));

    }

    @Override
    public int getItemCount() {
        return hikeName.size();
    }
    private Intent hikeIntent(int position){
        int selectedHikeID = Integer.parseInt(String.valueOf(hikeID.get(position)));


        Helper.longToastMessage(context, String.valueOf(selectedHikeID));
        Intent intent = new Intent(context, EditHikeActivity.class);
        intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
        return intent;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mainLayout;
        TextView hikeID;
        TextView hikeName;
        TextView hikeDescription;
        TextView hikeDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeID = itemView.findViewById(R.id.lblHikeID);
            hikeName = itemView.findViewById(R.id.lblHikeName);
            hikeDescription = itemView.findViewById(R.id.lblHikeDescription);
            hikeDate = itemView.findViewById(R.id.txtHikeDate);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
