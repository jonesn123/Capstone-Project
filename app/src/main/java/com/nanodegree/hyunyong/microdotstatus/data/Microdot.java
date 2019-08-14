package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;

public class Microdot {
    private float aqi;
    private float idx;
    ArrayList<Object> attributions = new ArrayList<Object>();
    City city;
    private String dominentpol;
    Iaqi iaqi;
    Time time;
    Debug debug;


    // Getter Methods

    public float getAqi() {
        return aqi;
    }

    public float getIdx() {
        return idx;
    }

    public City getCity() {
        return city;
    }

    public String getDominentpol() {
        return dominentpol;
    }

    public Iaqi getIaqi() {
        return iaqi;
    }

    public Time getTime() {
        return time;
    }

    public Debug getDebug() {
        return debug;
    }

    // Setter Methods

    public void setAqi(float aqi) {
        this.aqi = aqi;
    }

    public void setIdx(float idx) {
        this.idx = idx;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setDominentpol(String dominentpol) {
        this.dominentpol = dominentpol;
    }

    public void setIaqi(Iaqi iaqi) {
        this.iaqi = iaqi;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
}
