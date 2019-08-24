package com.nanodegree.hyunyong.microdotstatus.view;

import android.app.AlertDialog;
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

import com.nanodegree.hyunyong.microdotstatus.R;
import com.nanodegree.hyunyong.microdotstatus.data.Microdot;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;
import com.nanodegree.hyunyong.microdotstatus.databinding.CurrentAreaFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SelectedCityFragment extends DaggerFragment {

    private static final int REQUEST_CHECK_SETTINGS = 100;

    private CityViewModel mViewModel;
    private CurrentAreaFragmentBinding mBinding;

    public static SelectedCityFragment newInstance(float latitude, float longtitude) {
        SelectedCityFragment selectedCityFragment = new SelectedCityFragment();
        Bundle args = new Bundle();
        args.putFloat(SearchActivity.EXTRA_LATITUDE, latitude);
        args.putFloat(SearchActivity.EXTRA_LONGTITUDE, longtitude);
        selectedCityFragment.setArguments(args);
        return selectedCityFragment;
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
        // TODO: Use the ViewModel
        init();
    }

    private void init() {
        setClickListener();
        float latitude = getArguments().getFloat(SearchActivity.EXTRA_LATITUDE, 0);
        float longtitude = getArguments().getFloat(SearchActivity.EXTRA_LONGTITUDE, 0);

        mViewModel.getFeedFromLocation(latitude, longtitude).observe(this, new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState responseState) {
                Microdot data = responseState.getData();
                if (data == null) return;
                mBinding.setMicrodot(data);
                mBinding.setIaqi(data.getIaqi());
                mBinding.setCity(data.getCity());
                mBinding.setTime(data.getTime());
            }
        });
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