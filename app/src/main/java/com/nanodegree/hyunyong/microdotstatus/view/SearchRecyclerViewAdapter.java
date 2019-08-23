package com.nanodegree.hyunyong.microdotstatus.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.databinding.ItemCityBinding;

import java.util.List;

public class SearchRecyclerViewAdapter extends
        RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {

    private List<City> cities;
    private OnCityClickListener listener;
    SearchRecyclerViewAdapter(List<City> cities, OnCityClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    class SearchRecyclerViewHolder extends RecyclerView.ViewHolder {
        ItemCityBinding binding = DataBindingUtil.bind(itemView);

        SearchRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        void bindData(final int position) {
            binding.setCity(cities.get(position));
            binding.executePendingBindings();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new SearchRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    interface OnCityClickListener {
        void onClick(int position);
    }
}
