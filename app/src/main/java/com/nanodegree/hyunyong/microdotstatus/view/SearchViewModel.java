package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.CitiesResponse;
import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.data.Repository;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private Repository repository;

    private MutableLiveData citiesLiveData = new MutableLiveData<List<City>>();

    public LiveData<List<City>> getCitiesLiveData() {
        return citiesLiveData;
    }

    @Inject
    public SearchViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<CitiesResponse> fetchFeedFromKeyword(String keyword) {
        MediatorLiveData<CitiesResponse> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(repository.getFeedFromCity(keyword), new Observer<CitiesResponse>() {
            @Override
            public void onChanged(CitiesResponse citiesResponse) {
                citiesLiveData.setValue(citiesResponse.getCities());
            }
        });
        return mediatorLiveData;
    }
}
