package com.example.priyankanandiraju.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>> {

    private ListView listView;
    private TextView tvEmptyData;
    private NewsAdapter adapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        tvEmptyData = (TextView) findViewById(R.id.tv_no_data);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        adapter = new NewsAdapter(this, new ArrayList<NewsData>());

        listView.setAdapter(adapter);
        listView.setEmptyView(tvEmptyData);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsData currentItem = adapter.getItem(position);
                Uri newsDataUri;
                if (currentItem != null) {
                    newsDataUri = Uri.parse(currentItem.getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsDataUri);
                    startActivity(websiteIntent);
                }
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, MainActivity.this);
        } else {
            spinner.setVisibility(View.GONE);
            tvEmptyData.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, Bundle args) {
        return new NewsDataLoader(this, QueryUtils.QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> data) {
        adapter.clear();
        tvEmptyData.setText(R.string.no_news_data);
        spinner.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        adapter.clear();
    }
}
