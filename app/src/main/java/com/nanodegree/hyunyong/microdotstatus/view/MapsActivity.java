package com.nanodegree.hyunyong.microdotstatus.view;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nanodegree.hyunyong.microdotstatus.LocationManager;
import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.Map;
import com.nanodegree.hyunyong.microdotstatus.data.MapResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MapsActivity extends DaggerAppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private MapViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel.class);
        mViewModel.getMapLiveData().observe(this, new Observer<List<Map>>() {
            @Override
            public void onChanged(List<Map> maps) {
                for (Map map : maps) {
                    LatLng latLng = new LatLng(map.getLat(), map.getLon());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(map.getAqi()));
                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mLocationManager = new LocationManager(this, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received

                mMap = googleMap;
                LatLng currentArea = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());

                String latlng = String.valueOf(currentArea.latitude) +
                        "," + currentArea.longitude +
                        "," + (currentArea.latitude + 0.7) +
                        "," + (currentArea.longitude + 0.7);
                mViewModel.fetchMapData(latlng).observe(MapsActivity.this, new Observer<MapResponse>() {
                    @Override
                    public void onChanged(MapResponse mapResponse) {
                        // handling error
                        if (!mapResponse.getStatus().equals("ok")) {

                        }
                    }
                });
                mMap.addMarker(new MarkerOptions().position(currentArea).title("Current Area"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentArea));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        mLocationManager.updateLocation();

    }
}
