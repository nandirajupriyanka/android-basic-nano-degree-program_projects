package com.example.priyankanandiraju.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

/**
 * Created by priyankanandiraju on 1/12/17.
 */

public class NewsDataLoader extends AsyncTaskLoader<List<NewsData>> {

    private final String mUrl;

    public NewsDataLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<NewsData> loadInBackground() {
        URL url;
        try {
            url = new URL(mUrl);
            String jsonResponse = QueryUtils.makeHttpRequest(url);
            return QueryUtils.fetchNewsData(jsonResponse);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
