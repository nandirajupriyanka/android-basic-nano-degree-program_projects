package com.example.priyankanandiraju.newsapp;

/**
 * Created by priyankanandiraju on 1/12/17.
 */

public class NewsData {
    private String title;
    private String section;
    private String url;

    public NewsData(String title, String section, String url) {
        this.title = title;
        this.section = section;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
