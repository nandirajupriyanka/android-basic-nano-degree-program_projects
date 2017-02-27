package com.example.priyankanandiraju.booksearch;

import java.util.ArrayList;

/**
 * Created by priyankanandiraju on 12/30/16.
 */

public class Books {
    private String title;
    private ArrayList<String> authors;
    private String description;

    public Books(String title, ArrayList<String> authors, String description) {
        this.title = title;
        this.authors = authors;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Books{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", description='" + description + '\'' +
                '}';
    }
}
