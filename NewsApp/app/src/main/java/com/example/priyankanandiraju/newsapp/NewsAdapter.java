package com.example.priyankanandiraju.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by priyankanandiraju on 1/12/17.
 */

public class NewsAdapter extends ArrayAdapter<NewsData> {

    public NewsAdapter(Context context, ArrayList<NewsData> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSection = (TextView) view.findViewById(R.id.tv_section_name);

        NewsData currentItem = getItem(position);
        if (currentItem != null) {
            tvTitle.setText(currentItem.getTitle());
            tvSection.setText(currentItem.getSection());
        }

        return view;
    }
}
