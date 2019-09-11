package com.nanodegree.hyunyong.microdotstatus.view;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.nanodegree.hyunyong.microdotstatus.AQIWidget;
import com.nanodegree.hyunyong.microdotstatus.LocationManager;
import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.data.Microdot;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;
import com.nanodegree.hyunyong.microdotstatus.databinding.CurrentAreaFragmentBinding;
import com.nanodegree.hyunyong.microdotstatus.db.AppDatabase;
import com.nanodegree.hyunyong.microdotstatus.db.CityDao;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CurrentAreaFragment extends DaggerFragment {
    private CityViewModel mViewModel;

    private CurrentAreaFragmentBinding mBinding;
    private LocationManager mLocationManager;
    @Inject
    public AppDatabase mDatabase;

    public static CurrentAreaFragment newInstance() {
        return new CurrentAreaFragment();
    }

    private View.OnClickListener onAqiInformationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
            switch (v.getId()) {
                case R.id.aqi_good:
                    builder.setTitle(getString(R.string.good));
                    builder.setIcon(R.drawable.very_good);
                    builder.setMessage(getString(R.string.good_explain));
                    break;
                case R.id.aqi_moderate:
                    builder.setTitle(getString(R.string.moderate));
                    builder.setIcon(R.drawable.good);
                    builder.setMessage(getString(R.string.moderate_explain));
                    break;
                case R.id.aqi_bad:
                    builder.setTitle(getString(R.string.bad));
                    builder.setIcon(R.drawable.normal);
                    builder.setMessage(getString(R.string.bad_explain));
                    break;
                case R.id.aqi_so_bad:
                    builder.setTitle(getString(R.string.unhealthy));
                    builder.setIcon(R.drawable.bad);
                    builder.setMessage(getString(R.string.unhealthy_explain));
                    break;
                case R.id.aqi_very_bad:
                    builder.setTitle(getString(R.string.very_unhealthy));
                    builder.setIcon(R.drawable.bad);
                    builder.setMessage(getString(R.string.very_unhealthy_explain));
                    break;
                case R.id.aqi_extremely_bad:
                    builder.setTitle(getString(R.string.hazardous));
                    builder.setIcon(R.drawable.very_bad);
                    builder.setMessage(getString(R.string.hazardous_explain));
                    break;
            }

            builder.show();
        }
    };

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.current_area_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(CityViewModel.class);
        init();
        mLocationManager.updateLocation();
    }

    private void init() {
        if (getActivity() == null) return;

        mLocationManager = new LocationManager(getActivity(), new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received

                if (getActivity() == null) return;
                mViewModel.getFeedFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()).observe(getActivity(), new Observer<ResponseState>() {
                    @Override
                    public void onChanged(ResponseState s) {
                        Microdot data = s.getData();
                        if (data == null) return;
                        mBinding.setMicrodot(data);
                        mBinding.setIaqi(data.getIaqi());
                        mBinding.setCity(data.getCity());
                        mBinding.setTime(data.getTime());

                        // delete current data of widget
                        CityDao cityDao = mDatabase.cityDao();
                        List<City> currentCities = cityDao.getCities(true, true);
                        if (currentCities.size() != 0) {
                            City currentCity = currentCities.get(0);
                            cityDao.delete(currentCity);
                        }

                        // insert new data of widget
                        City city = data.getCity();
                        city.setCurrentCity(true);
                        city.setWidget(true);
                        cityDao.insert(city);

                        Context context = getContext();
                        if (context == null) {
                            return;
                        }

                        // notify to widget
                        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                        intent.setComponent(new ComponentName(context, AQIWidget.class));
                        context.sendBroadcast(intent);
                    }
                });

            }
        });

        setClickListener();
    }

    private void setClickListener() {
        View rootView = mBinding.getRoot();
        rootView.findViewById(R.id.aqi_good).setOnClickListener(onAqiInformationClickListener);
        rootView.findViewById(R.id.aqi_moderate).setOnClickListener(onAqiInformationClickListener);
        rootView.findViewById(R.id.aqi_bad).setOnClickListener(onAqiInformationClickListener);
        rootView.findViewById(R.id.aqi_so_bad).setOnClickListener(onAqiInformationClickListener);
        rootView.findViewById(R.id.aqi_very_bad).setOnClickListener(onAqiInformationClickListener);
        rootView.findViewById(R.id.aqi_extremely_bad).setOnClickListener(onAqiInformationClickListener);
    }

}
