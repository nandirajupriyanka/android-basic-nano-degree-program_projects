package com.example.priyankanandiraju.newsapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyankanandiraju on 1/12/17.
 */

public class QueryUtils {

    public static final String QUERY_URL = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";

    public static String makeHttpRequest(URL url) throws IOException {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder outputString = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                outputString.append(line);
                line = bufferedReader.readLine();
            }
        }
        return outputString.toString();
    }

    public static List<NewsData> fetchNewsData(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<NewsData> newsDataList = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            if (rootObject.has("response")) {
                JSONObject responseObject = rootObject.getJSONObject("response");
                if (responseObject.has("results")) {
                    JSONArray resultsArray = responseObject.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject object = resultsArray.getJSONObject(i);
                        String title = object.optString("webTitle");
                        String section = object.optString("sectionName");
                        String url = object.optString("webUrl");

                        NewsData newsData = new NewsData(title, section, url);
                        newsDataList.add(newsData);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsDataList;
    }
}
