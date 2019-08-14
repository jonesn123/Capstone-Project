package com.nanodegree.hyunyong.microdotstatus.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {

    @GET("feed/{city}/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    public Call<String> getFeed(@Path("city") String city);

    @GET("feed/geo:{latitude};{longtitude}/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    public Call<ResponseState> getFeedByLocation(@Path("latitude") double latitude, @Path("longtitude") double longtitude);
}
