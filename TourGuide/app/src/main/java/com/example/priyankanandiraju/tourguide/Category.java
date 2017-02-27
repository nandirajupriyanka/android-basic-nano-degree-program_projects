package com.example.priyankanandiraju.tourguide;

/**
 * Created by priyankanandiraju on 11/24/16.
 */

public class Category {
    private static final int NO_IMAGE = -1;
    private String title;
    private String location;
    private String date;
    private int image = NO_IMAGE;

    public Category(String title, String location, String date, int image) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.image = image;
    }

    public Category(String title, String location, String date) {
        this.title = title;
        this.location = location;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public boolean hasImage() {
        return image != NO_IMAGE;
    }

}
