package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;

public class Microdot {
    private float aqi;
    City city;
    Iaqi iaqi;
    Time time;


    // Getter Methods

    public float getAqi() {
        return aqi;
    }

    public City getCity() {
        return city;
    }

    public Iaqi getIaqi() {
        return iaqi;
    }

    public Time getTime() {
        return time;
    }

    public void setAqi(float aqi) {
        this.aqi = aqi;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setIaqi(Iaqi iaqi) {
        this.iaqi = iaqi;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
