package com.nanodegree.hyunyong.microdotstatus.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nanodegree.hyunyong.microdotstatus.data.Repository;
import com.nanodegree.hyunyong.microdotstatus.data.Webservice;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class})
abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);

    @Provides
    static SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    @Provides
    static Repository provideRepository(Webservice webservice, SharedPreferences sharedPreferences) {
        return new Repository(webservice, sharedPreferences);
    }
}
