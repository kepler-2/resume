package com.kepler.resume.datebaseHelper;

public class URLSetterGetter {

    private int id;
    private String url;

    public URLSetterGetter(String url) {
        this.url = url;
    }

    public URLSetterGetter() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

}
