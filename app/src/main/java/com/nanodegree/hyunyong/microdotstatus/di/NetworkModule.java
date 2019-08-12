package com.nanodegree.hyunyong.microdotstatus.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {AppModule.class})
class NetworkModule {
    @Singleton
    @Provides
    static GsonConverterFactory provideGSONConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkhttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(client)
                .addConverterFactory(factory)
                .build();
    }
}