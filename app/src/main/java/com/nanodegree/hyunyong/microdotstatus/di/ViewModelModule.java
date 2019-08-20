package com.nanodegree.hyunyong.microdotstatus.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nanodegree.hyunyong.microdotstatus.view.CurrentAreaViewModel;
import com.nanodegree.hyunyong.microdotstatus.view.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CurrentAreaViewModel.class)
    abstract ViewModel bindCurrentAreaViewModel(CurrentAreaViewModel currentAreaViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DaggerViewModelFactory factory);
}
