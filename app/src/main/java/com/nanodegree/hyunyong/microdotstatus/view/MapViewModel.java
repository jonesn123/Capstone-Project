package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.CitiesResponse;
import com.nanodegree.hyunyong.microdotstatus.data.Map;
import com.nanodegree.hyunyong.microdotstatus.data.MapResponse;
import com.nanodegree.hyunyong.microdotstatus.data.Repository;

import java.util.List;

import javax.inject.Inject;

public class MapViewModel extends ViewModel {

    private Repository repository;
    public MutableLiveData<List<Map>> mapLiveData = new MutableLiveData<>();
    public LiveData<List<Map>> getMapLiveData() {
        return mapLiveData;
    }

    @Inject
    public MapViewModel(Repository repository) {
        this.repository = repository;
    }


    public LiveData<MapResponse> fetchMapData(String latlng) {
        MediatorLiveData<MapResponse> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(
                repository.getMapInformation(latlng), new Observer<MapResponse>() {
                    @Override
                    public void onChanged(MapResponse mapResponse) {
                        if (mapResponse.getStatus().equals("ok")) {
                            mapLiveData.setValue(mapResponse.getData());
                        }
                    }
                });
        return mediatorLiveData;
    }

}
