package com.sample.droider.legacyrecipeapp.api;

public class RequestTag {

    private String url;
    private long timeStamp;

    public RequestTag(String url) {
        this.url = url;
        this.timeStamp = System.currentTimeMillis();
    }

    public String getTag() {
        return url + ":" + timeStamp;
    }
}