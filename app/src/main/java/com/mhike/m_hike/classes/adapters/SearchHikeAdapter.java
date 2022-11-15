package com.mhike.m_hike.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.models.Hike;

import java.util.ArrayList;

public class SearchHikeAdapter extends RecyclerView.Adapter<SearchHikeAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Hike> courseModelArrayList;
    private ArrayList<Hike> HikeList;
//https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/

    // creating a constructor for our variables.
    public SearchHikeAdapter(ArrayList<Hike> HikeList, Context context) {
        this.HikeList = HikeList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Hike> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        courseModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchHikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHikeAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Hike model = courseModelArrayList.get(position);
        holder.courseNameTV.setText(model.getHikeName());
        holder.courseDescTV.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return HikeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView courseNameTV;
        private final TextView courseDescTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.lblHikeNameSearch);
            courseDescTV = itemView.findViewById(R.id.lblHikeDescriptionSearch);
        }
    }
}