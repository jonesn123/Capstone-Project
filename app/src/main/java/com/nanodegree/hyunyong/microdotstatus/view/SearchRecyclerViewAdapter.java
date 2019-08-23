package com.nanodegree.hyunyong.microdotstatus.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SearchRecyclerViewHolder extends RecyclerView.ViewHolder {
        public SearchRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
