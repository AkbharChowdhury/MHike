package com.mhike.m_hike.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.models.CourseModel;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Hike> hikeList;

    // creating a constructor for our variables.
    public SearchAdapter(ArrayList<Hike> hikeList, Context context) {
        this.hikeList = hikeList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Hike> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        hikeList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Hike hike = hikeList.get(position);
        holder.hikeName.setText(hike.getHikeName());
        holder.hikeDescription.setText(hike.getDescription());
        holder.hikeDate.setText(Helper.formatDate(hike.getHikeDate()));

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return hikeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView hikeName;
        private final TextView hikeDescription;
        private final TextView hikeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            hikeName = itemView.findViewById(R.id.lblHikeNameSearch);
            hikeDescription = itemView.findViewById(R.id.lblHikeDescriptionSearch);
            hikeDate = itemView.findViewById(R.id.lblHikeDateSearch);

        }
    }
}