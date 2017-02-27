package com.example.priyankanandiraju.booksearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSearch;
    private EditText etQuery;
    private ListView listView;
    private BookSearchAdapter adapter;
    private String searchQuery;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btn_search);
        etQuery = (EditText) findViewById(R.id.et_query);
        listView = (ListView) findViewById(R.id.listView);
        noData = (TextView) findViewById(R.id.tv_no_data);

        adapter = new BookSearchAdapter(MainActivity.this, new ArrayList<Books>());
        listView.setAdapter(adapter);
        btnSearch.setOnClickListener(this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                if (isNetworkAvailable()) {
                    searchQuery = etQuery.getText().toString();
                    if (!TextUtils.equals(searchQuery, "")) {
                        searchQuery = searchQuery.replaceAll("\\s+", "+");
                        new QuerySearchAsync().execute();
                    } else {
                        Toast.makeText(this, "Please enter text to search", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No INTERNET Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class QuerySearchAsync extends AsyncTask<String, Void, List<Books>> {


        @Override
        protected List<Books> doInBackground(String... strings) {
            try {
                URL url = BookSearchQuery.createUrl(searchQuery);
                String jsonResponse = BookSearchQuery.makeHttpRequest(url);
                return BookSearchQuery.parseResponse(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Books> response) {
            adapter.clear();
            if (response != null && !response.isEmpty()) {
                noData.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                adapter.addAll(response);
            } else {
                listView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                etQuery.setText("");
            }

        }
    }
}
