package com.nanodegree.hyunyong.microdotstatus.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nanodegree.hyunyong.microdotstatus.BindingUtil;
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

                    try {
                        LayoutInflater inflater = LayoutInflater.from(MapsActivity.this);
                        View view = LayoutInflater.from(MapsActivity.this).inflate(R.layout.marker_layout, null);
                        ((ImageView) view.findViewById(R.id.iv_icon)).setImageResource(BindingUtil.getDrawableResourceByAqi(map.getAqi()));
                        ((TextView) view.findViewById(R.id.tv_marker)).setText(String.valueOf(map.getAqi()));
                        view.findViewById(R.id.background).setBackgroundResource(BindingUtil.getColorResourceByAqi(map.getAqi()));

                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(latLng)
                                        .title(getString(R.string.select))
                                        .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(view))));
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private Bitmap createDrawableFromView(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
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

                String latlng = (currentArea.latitude - 5) +
                        "," + (currentArea.longitude - 5) +
                        "," + (currentArea.latitude + 5) +
                        "," + (currentArea.longitude + 5);
                mViewModel.fetchMapData(latlng).observe(MapsActivity.this, new Observer<MapResponse>() {
                    @Override
                    public void onChanged(MapResponse mapResponse) {
                        // handling error
                    }
                });
                mMap.addMarker(new MarkerOptions().position(currentArea).title(getString(R.string.current_area)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentArea));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
        });
        mLocationManager.updateLocation();

    }
}
