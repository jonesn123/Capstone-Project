package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {

    @GET("feed/{city}/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    Call<String> getFeed(@Path("city") String city);

    @GET("feed/geo:{latitude};{longtitude}/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    Call<ResponseState> getFeedByLocation(@Path("latitude") double latitude, @Path("longtitude") double longtitude);

    @GET("search/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    Call<CitiesResponse> getFeedByKeyword(@Query("keyword") String keyword);

    //39.379436,116.091230,40.235643,116.784382&
    @GET("map/bounds/?token=03e45b511c0ed129eaf05889c2be73ab1ad260a5")
    Call<MapResponse> getMapInformation(@Query("latlng") String latlng);
}
