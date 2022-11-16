package com.mhike.m_hike.classes.adapters;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.activities.HikeDetailsActivity;
import com.mhike.m_hike.activities.AddObservationActivity;
import com.mhike.m_hike.activities.EditHikeActivity;
import com.mhike.m_hike.R;
import com.mhike.m_hike.activities.ViewHikeObservationActivity;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.enums.ActivityForm;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Hike> hikeList;
    private Activity activity;
    private final ActivityForm activityForm;

    public HikeAdapter(ArrayList<Hike> hikeList, Activity activity, Context context, ActivityForm activityForm){
        this.activity = activity;
        this.context = context;
        this.activityForm = activityForm;
        this.hikeList = hikeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hike_row, parent, false);
        return new MyViewHolder(view);



    }
    

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hike hike = hikeList.get(position);
        // Note: the hike ID MUST be in String format to set the next
        holder.hikeID.setText(String.valueOf(hike.getHikeID()));
        holder.hikeName.setText(hike.getHikeName());
        holder.hikeDescription.setText(hike.getDescription());
        holder.hikeDate.setText(Helper.formatDate(hike.getHikeDate()));
        holder.mainLayout.setOnClickListener(view -> activity.startActivityForResult(hikeIntent(position), 1));

    }

    @Override
    public int getItemCount() {
        return hikeList.size();
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

            case SEARCH_HIKE:
                activityToOpen =  HikeDetailsActivity.class;
                break;
            default:
                Helper.showActivityFormErrorMessage();

        }

            Intent intent = new Intent(context, activityToOpen);
            intent.putExtra("hikeID", String.valueOf(hikeList.get(position).getHikeID()));
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
