package com.example.priyankanandiraju.booksearch;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyankanandiraju on 12/29/16.
 */

class BookSearchQuery {

    private static final String APP_KEY = "AIzaSyAg0eRBAtxTpTgxa6eDl5lpa2nrmxjwheQ";
    private static final String TAG = "BookSearchQuery";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";


    static URL createUrl(String searchQuery) {
        String queryUrl = BASE_URL + searchQuery + "&key=" + APP_KEY;
        URL url = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }

        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(TAG, "status code " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }

        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output = output.append(line);
                line = bufferedReader.readLine();

            }
        }

        return output.toString();
    }

    static List<Books> parseResponse(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            List<Books> booksList = new ArrayList<>();
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = rootObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfoObject = item.getJSONObject("volumeInfo");
                ArrayList<String> authors = new ArrayList<>();

                String title = volumeInfoObject.optString("title");
                String description = volumeInfoObject.optString("description");

                if (volumeInfoObject.has("authors")) {
                    JSONArray authorsJSONArray = volumeInfoObject.getJSONArray("authors");
                    for (int j = 0; j < authorsJSONArray.length(); j++) {
                        authors.add(authorsJSONArray.optString(j));
                    }
                }

                Books book = new Books(title, authors, description);
                booksList.add(book);
                Log.v("TAG", book.toString());
            }
            return booksList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
