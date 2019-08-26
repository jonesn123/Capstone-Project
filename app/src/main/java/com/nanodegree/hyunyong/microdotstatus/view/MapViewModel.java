package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.Repository;

import javax.inject.Inject;

public class MapViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public MapViewModel(Repository repository) {
        this.repository = repository;
    }

}
