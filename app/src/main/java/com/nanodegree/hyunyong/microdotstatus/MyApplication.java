package com.nanodegree.hyunyong.microdotstatus;

import com.nanodegree.hyunyong.microdotstatus.di.AppComponent;
import com.nanodegree.hyunyong.microdotstatus.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent component = DaggerAppComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }
}
