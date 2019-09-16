package com.nanodegree.hyunyong.microdotstatus.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nanodegree.hyunyong.microdotstatus.Utils;
import com.nanodegree.hyunyong.microdotstatus.LocationManager;
import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.Map;
import com.nanodegree.hyunyong.microdotstatus.data.MapResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.nanodegree.hyunyong.microdotstatus.view.SearchActivity.EXTRA_CITY_NAME;
import static com.nanodegree.hyunyong.microdotstatus.view.SearchActivity.EXTRA_LATITUDE;
import static com.nanodegree.hyunyong.microdotstatus.view.SearchActivity.EXTRA_LONGTITUDE;

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
                        View view = LayoutInflater.from(MapsActivity.this).inflate(R.layout.marker_layout, null);
                        ((ImageView) view.findViewById(R.id.iv_icon)).setImageResource(Utils.getDrawableResourceByAqi(map.getAqi()));
                        ((TextView) view.findViewById(R.id.tv_marker)).setText(String.valueOf(map.getAqi()));
                        view.findViewById(R.id.background).setBackgroundResource(Utils.getColorResourceByAqi(map.getAqi()));

                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(view)));
                        mMap.addMarker(markerOptions);

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                LatLng position = marker.getPosition();
                                Geocoder gcd = new Geocoder(MapsActivity.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = gcd.getFromLocation(position.latitude, position.longitude, 1);
                                    if (addresses.size() > 0) {
                                        String addressLine = addresses.get(0).getAddressLine(0);
                                        String[] address = addressLine.split(" ");

                                        marker.setTitle(address[2] + " " + address[3] + " " + address[4]);
                                    } else {
                                        marker.setTitle(getString(R.string.select));
                                    }
                                } catch (IOException e) {
                                }

                                return false;
                            }
                        });
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                LatLng position = marker.getPosition();
                                Intent intent = new Intent();

                                intent.putExtra(EXTRA_CITY_NAME, marker.getTitle());
                                intent.putExtra(EXTRA_LATITUDE, position.latitude);
                                intent.putExtra(EXTRA_LONGTITUDE, position.longitude);
                                Log.e("marker", "position : " + position.latitude + "," + position.longitude);
                                setResult(MainActivity.RESULT_OK, intent);
                                finish();
                            }
                        });
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
