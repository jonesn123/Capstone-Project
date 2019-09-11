package com.nanodegree.hyunyong.microdotstatus.view;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodegree.hyunyong.microdotstatus.AQIWidget;
import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.CitiesResponse;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.databinding.ActivitySearchBinding;
import com.nanodegree.hyunyong.microdotstatus.db.CityDao;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SearchActivity extends DaggerAppCompatActivity {
    public static String EXTRA_CITY_NAME = "cityname";
    public static String EXTRA_LATITUDE = "latitude";
    public static String EXTRA_LONGTITUDE = "longtitude";
    public static String EXTRA_FROM_WIDGET = "fromwidget";
    private SearchViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    public CityDao cityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setTitle(R.string.search_location);
        Intent intent = getIntent();
        final boolean isFromWidget = intent.getBooleanExtra(EXTRA_FROM_WIDGET, false);


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
                                City city = cities.get(position);
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_CITY_NAME, city.getName());
                                intent.putExtra(EXTRA_LATITUDE, city.getGeo().get(0));
                                intent.putExtra(EXTRA_LONGTITUDE, city.getGeo().get(1));
                                if (isFromWidget) {
                                    // notify to widget
                                    City widget = cityDao.getCity(true);
                                    if (widget != null) {
                                        cityDao.delete(widget);
                                    }
                                    city.setWidget(true);
                                    cityDao.insert(city);

                                    Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    widgetIntent.setComponent(new ComponentName(SearchActivity.this, AQIWidget.class));
                                    widgetIntent.putExtra(EXTRA_FROM_WIDGET, true);
                                    sendBroadcast(widgetIntent);
                                    finishAffinity();
                                } else {
                                    setResult(MainActivity.RESULT_OK, intent);
                                    finish();
                                }
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
