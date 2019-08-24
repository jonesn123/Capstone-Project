package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;
import java.util.List;

public class City {
    List<Float> geo = new ArrayList<>();
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Float> getGeo() {
        return geo;
    }

    public void setGeo(List<Float> geo) {
        this.geo = geo;
    }
}
