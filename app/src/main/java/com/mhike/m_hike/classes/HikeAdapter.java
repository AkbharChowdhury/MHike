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

import com.mhike.m_hike.AddHikeActivity;
import com.mhike.m_hike.AddObservationActivity;
import com.mhike.m_hike.EditHikeActivity;
import com.mhike.m_hike.MainActivity;
import com.mhike.m_hike.ObservationActivity;
import com.mhike.m_hike.R;
import com.mhike.m_hike.ViewHikeObservationActivity;
import com.mhike.m_hike.classes.enums.ActivityForm;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList hikeName;
    private ArrayList hikeDescription;
    private ArrayList hikeDate;
    private ArrayList hikeID;
    private Activity activity;
//    private boolean isHikeForms;
    private final ActivityForm activityForm;


    public HikeAdapter(
            Activity activity,
            Context context,
//            boolean isHikeForms,
            ActivityForm activityForm,


            ArrayList hikeID,
                       ArrayList hikeName,
                       ArrayList hikeDescription,
                       ArrayList hikeDate) {
        this.activity = activity;
        this.context = context;
//        this.isHikeForms = isHikeForms;
        this.activityForm = activityForm;


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
        holder.hikeDate.setText(Helper.formatDateShort(String.valueOf(hikeDate.get(position))));
        holder.mainLayout.setOnClickListener(view -> activity.startActivityForResult(hikeIntent(position), 1));



    }

    @Override
    public int getItemCount() {
        return hikeID.size();
    }

    private Intent hikeIntent(int position){

         Class<? extends Activity> activityToOpen =  AddObservationActivity.class;

        switch (activityForm) {
            case OBSERVATION_RECYCLER:
                activityToOpen =  ViewHikeObservationActivity.class;
                break;

            case HIKE_RECYCLER:
                activityToOpen =  EditHikeActivity.class;
                break;
            default:
                Helper.showActivityFormErrorMessage();

        }

            Intent intent = new Intent(context, activityToOpen);
            intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
            return intent;

//        if (activityForm == ActivityForm.HIKE_RECYCLER) {
//            Intent intent;
//            intent = new Intent(context, EditHikeActivity.class);
//            intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
//            return intent;
//        }
//        else{
//            Intent intent;
//            intent = new Intent(context, ViewHikeObservationActivity.class);
//            intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
//            return intent;
//        }


//        else if(activityForm == ActivityForm.OBSERVATION_RECYCLER){
//            Intent intent;
//            intent = new Intent(context, ViewHikeObservationActivity.class);
//            return intent;
//
//        }



//        if (isHikeForms) {
//            Intent intent;
//            intent = new Intent(context, EditHikeActivity.class);
//            intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
//            return intent;
//        } else{
//            Intent intent;
//            intent = new Intent(context, ViewHikeObservationActivity.class);
//            return intent;
//
//        }

//        switch (activityForm){
//            case HIKE_RECYCLER:
//                intent = new Intent(context, EditHikeActivity.class);
//                intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
//                return intent;
//            case OBSERVATION_RECYCLER:
//                intent = new Intent(context, ViewHikeObservationActivity.class);
//                intent.putExtra("hikeID", String.valueOf(hikeID.get(position)));
//                return intent;
//
//        }
//        return new Intent();

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
