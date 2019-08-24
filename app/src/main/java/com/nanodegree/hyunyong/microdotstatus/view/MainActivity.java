package com.nanodegree.hyunyong.microdotstatus.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
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

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_SEARCH = 0;
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 2;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton search, location, add;
    private TabFragmentPagerAdapter adapter;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
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
                        mRequestingLocationUpdates = true;
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

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CurrentAreaFragment.newInstance(), getResources().getString(R.string.current_city));
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_search:
                anim();
                Intent intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_SEARCH);
                break;
            case R.id.fab_location:
                anim();
                Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_add:
                anim();
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
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
                float latitude = data.getFloatExtra(SearchActivity.EXTRA_LATITUDE, 0);
                float longtitude = data.getFloatExtra(SearchActivity.EXTRA_LONGTITUDE, 0);

                String simpleCityName = cityName.split(",|\\;")[0];
                Fragment fragment = SelectedCityFragment.newInstance(latitude, longtitude);
                adapter.addFragment(fragment, simpleCityName);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                TabLayout.Tab tab = tabLayout.getTabAt(adapter.getCount() - 1);
                tab.select();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
