package com.nanodegree.hyunyong.microdotstatus.di;

import com.nanodegree.hyunyong.microdotstatus.view.CurrentAreaFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract CurrentAreaFragment contributeCurrentFragment();
}
