package com.superzmz.www.bean;


public class ApplyType {

    public ApplyType(String name, String url) {
        this.name = name;
        this.url = url;
    }

    String name;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
