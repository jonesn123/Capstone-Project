package com.nanodegree.hyunyong.microdotstatus.data;

public class Map {
    private float lat;
    private float lon;
    private float uid;
    private String aqi;


    // Getter Methods

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public float getUid() {
        return uid;
    }

    public float getAqi() {
        try {
            return Float.parseFloat(aqi);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Setter Methods

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setUid(float uid) {
        this.uid = uid;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}