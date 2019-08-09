package com.nanodegree.hyunyong.microdotstatus.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);
}
