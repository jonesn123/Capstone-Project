package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.Repository;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;

import javax.inject.Inject;

public class CityViewModel extends ViewModel {

    private Repository repository;
    @Inject
    public CityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<ResponseState> getFeedFromLocation(double latitude, double longtitude) {
        return repository.getFeedFromLocation(latitude, longtitude);
    }
}
