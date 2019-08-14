package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;

public class City {
    ArrayList < Object > geo = new ArrayList< Object >();
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
}
