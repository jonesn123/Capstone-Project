package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.CitiesResponse;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;
import com.nanodegree.hyunyong.microdotstatus.databinding.ActivitySearchBinding;
import com.nanodegree.hyunyong.microdotstatus.di.DaggerAppComponent;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SearchActivity extends DaggerAppCompatActivity {


    private SearchViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setTitle(R.string.search_location);

        final RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        mViewModel.getCitiesLiveData().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(final List<City> cities) {
                binding.setCities(cities);
                final SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(cities,
                        new SearchRecyclerViewAdapter.OnCityClickListener() {
                            @Override
                            public void onClick(int position) {
                                Toast.makeText(SearchActivity.this,
                                        cities.get(position).getName(), Toast.LENGTH_LONG).show();
                            }
                        });
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                mViewModel.fetchFeedFromKeyword(s).observe(SearchActivity.this, new Observer<CitiesResponse>() {
                    @Override
                    public void onChanged(CitiesResponse cities) {
                        Log.d("cities", "size of city :" + cities.getStatus());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
