package com.nanodegree.hyunyong.microdotstatus.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);

    @Provides
    static SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }
}
