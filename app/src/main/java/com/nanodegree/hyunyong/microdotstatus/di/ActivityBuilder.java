package com.nanodegree.hyunyong.microdotstatus.di;

import com.nanodegree.hyunyong.microdotstatus.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
