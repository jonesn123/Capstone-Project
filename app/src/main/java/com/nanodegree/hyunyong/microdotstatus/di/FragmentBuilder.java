package com.nanodegree.hyunyong.microdotstatus.di;

import com.nanodegree.hyunyong.microdotstatus.view.CurrentAreaFragment;
import com.nanodegree.hyunyong.microdotstatus.view.SelectedCityFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract CurrentAreaFragment contributeCurrentFragment();

    @ContributesAndroidInjector
    abstract SelectedCityFragment contributeSelectedCityFragment();
}
