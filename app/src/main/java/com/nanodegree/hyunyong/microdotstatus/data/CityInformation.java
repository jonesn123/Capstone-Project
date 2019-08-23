package com.nanodegree.hyunyong.microdotstatus.data;

public class CityInformation {
    String aqi;
    City station;
    Time time;


    // Getter Methods

    public String getAqi() {
        return aqi;
    }

    public City getCity() {
        return station;
    }

    public Time getTime() {
        return time;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public void setCity(City city) {
        this.station = city;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
