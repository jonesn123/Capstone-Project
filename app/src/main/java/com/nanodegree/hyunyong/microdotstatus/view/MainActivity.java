package com.nanodegree.hyunyong.microdotstatus.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nanodegree.hyunyong.microdotstatus.BuildConfig;
import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.Utils;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.db.AppDatabase;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_SEARCH = 0;
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 2;
    private static final int MAX_CITY = 20;
    private static final int PAGE_SIZE = 2;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton search, location, add;
    private TabFragmentPagerAdapter adapter;
    @Inject
    public AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(PAGE_SIZE);
        tabLayout.setupWithViewPager(viewPager);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        search = findViewById(R.id.fab_search);
        location = findViewById(R.id.fab_location);
        add = findViewById(R.id.fab_add);

        search.setOnClickListener(this);
        location.setOnClickListener(this);
        add.setOnClickListener(this);

        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // current Fragment set
                        setupViewPager(viewPager);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    public void removeFragment(Fragment fragment) {
        adapter.removeFragment(fragment);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CurrentAreaFragment.newInstance(), getResources().getString(R.string.current_city));
        viewPager.setAdapter(adapter);
        addFragmentFromDatabase();
    }

    private void addFragmentFromDatabase() {
        List<City> cites = database.cityDao().getCities();
        for (City city : cites) {
            addFragmentFromCity(city.getName(), city.getGeo().get(0), city.getGeo().get(1));
        }
    }

    private void addFragmentFromCity(String cityName, double latitude, double longtitude) {
        String simpleCityName = Utils.getSimpleCityName(cityName);
        Fragment fragment = SelectedCityFragment.newInstance(latitude, longtitude);
        adapter.addFragment(fragment, simpleCityName);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_search:
                anim();
                startActivityForResult(new Intent(this, SearchActivity.class), REQUEST_SEARCH);
                break;
            case R.id.fab_location:
                anim();
                startActivityForResult(new Intent(this, MapsActivity.class), REQUEST_SEARCH);
                break;
            case R.id.fab_add:
                if(adapter.getCount() > MAX_CITY) {
                    Toast.makeText(this, R.string.max_city, Toast.LENGTH_LONG).show();
                } else {
                    anim();
                }
                break;
        }
    }

    private void anim() {

        if (isFabOpen) {
            search.startAnimation(fab_close);
            location.startAnimation(fab_close);
            search.setClickable(false);
            location.setClickable(false);
            isFabOpen = false;
        } else {
            search.startAnimation(fab_open);
            location.startAnimation(fab_open);
            search.setClickable(true);
            location.setClickable(true);
            isFabOpen = true;
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SEARCH) {
            if (resultCode == RESULT_OK) {
                String cityName = data.getStringExtra(SearchActivity.EXTRA_CITY_NAME);
                double latitude = data.getDoubleExtra(SearchActivity.EXTRA_LATITUDE, 0);
                double longtitude = data.getDoubleExtra(SearchActivity.EXTRA_LONGTITUDE, 0);

                addFragmentFromCity(cityName, latitude, longtitude);
                TabLayout tabLayout = findViewById(R.id.tabs);
                TabLayout.Tab tab = tabLayout.getTabAt(adapter.getCount() - 1);
                tab.select();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
