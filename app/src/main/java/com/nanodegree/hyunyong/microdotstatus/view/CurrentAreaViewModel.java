package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.Repository;

import javax.inject.Inject;

public class CurrentAreaViewModel extends ViewModel {

    private Repository repository;
    @Inject
    public CurrentAreaViewModel(Repository repository) {
        this.repository = repository;
    }

}
