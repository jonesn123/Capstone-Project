package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.lifecycle.ViewModel;

import com.nanodegree.hyunyong.microdotstatus.data.Repository;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private Repository repository;

    public SearchViewModel() {

    }

    @Inject
    public SearchViewModel(Repository repository) {
        this.repository = repository;
    }
}
