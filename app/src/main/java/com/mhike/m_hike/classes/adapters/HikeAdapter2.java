package com.mhike.m_hike.classes.adapters;


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

import com.mhike.m_hike.R;
import com.mhike.m_hike.activities.AddObservationActivity;
import com.mhike.m_hike.activities.EditHikeActivity;
import com.mhike.m_hike.activities.HikeDetailsActivity;
import com.mhike.m_hike.activities.ViewHikeObservationActivity;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;

public class HikeAdapter2 extends RecyclerView.Adapter<HikeAdapter2.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Hike> hikeList;
    private Activity activity;
    private Context context;
    private final ActivityForm activityForm;




    // creating a constructor for our variables.
    public HikeAdapter2(ArrayList<Hike> hikeList, Context context, Activity activity, ActivityForm activityForm) {
        this.hikeList = hikeList;
        this.context = context;
        this.activity = activity;
        this.activityForm = activityForm;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Hike> filteredList) {
        // below line is to add our filtered
        // list in our course array list.
        hikeList = filteredList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HikeAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HikeAdapter2.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Hike hike = hikeList.get(position);
        // Note: the hike ID MUST be in String format to set the next
        holder.hikeID.setText(String.valueOf(hike.getHikeID()));
        holder.hikeName.setText(hike.getHikeName());
        holder.hikeDescription.setText(hike.getDescription());
        holder.hikeDate.setText(Helper.formatDate(hike.getHikeDate()));
        holder.mainSearchLayout.setOnClickListener(view -> activity.startActivityForResult(hikeIntent(position), 1));

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView hikeID;
        private final TextView hikeName;
        private final TextView hikeDescription;
        private final TextView hikeDate;
        private final CardView mainSearchLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            hikeID = itemView.findViewById(R.id.lblHikeIDSearch);
            hikeName = itemView.findViewById(R.id.lblHikeNameSearch);
            hikeDescription = itemView.findViewById(R.id.lblHikeDescriptionSearch);
            hikeDate = itemView.findViewById(R.id.lblHikeDateSearch);
            mainSearchLayout  = itemView.findViewById(R.id.mainSearchLayout);
        }
    }
}