package com.nanodegree.hyunyong.microdotstatus.data;

public class Microdot {
    private double aqi;
    City city;
    Iaqi iaqi;
    Time time;


    // Getter Methods

    public double getAqi() {
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
