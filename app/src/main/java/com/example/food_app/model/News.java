package com.example.food_app.model;

public class News {
    private String title;
    private int banner;
    private double rate;
    private String views;
    private String url;

    public News(String title, int banner, double rate, String views, String url) {
        this.title = title;
        this.banner = banner;
        this.rate = rate;
        this.views = views;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
