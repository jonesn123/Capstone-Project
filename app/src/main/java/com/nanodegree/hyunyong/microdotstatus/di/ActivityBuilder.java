package com.nanodegree.hyunyong.microdotstatus.di;

import com.nanodegree.hyunyong.microdotstatus.view.MainActivity;
import com.nanodegree.hyunyong.microdotstatus.view.MapsActivity;
import com.nanodegree.hyunyong.microdotstatus.view.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract SearchActivity contributeSearchActivity();

    @ContributesAndroidInjector
    abstract MapsActivity contributeMapActivity();
}
