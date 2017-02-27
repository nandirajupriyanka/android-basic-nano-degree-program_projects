package com.example.priyankanandiraju.booksearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyankanandiraju on 12/30/16.
 */

class BookSearchAdapter extends ArrayAdapter<Books> {

    BookSearchAdapter(Context context, List<Books> booksArrayList) {
        super(context, 0, booksArrayList);
    }

    private static String removeFirstChar(String str) {
        return str.substring(2);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (convertView == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.book_row_item, parent, false);

        Books currentBook = getItem(position);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) view.findViewById(R.id.tv_description);
        TextView tvAuthors = (TextView) view.findViewById(R.id.tv_author);

        if (currentBook != null) {
            tvTitle.setText(currentBook.getTitle());
            tvDesc.setText(currentBook.getDescription());
            tvAuthors.setText(prepareAuthors(currentBook.getAuthors()));
        }
        return view;
    }

    private String prepareAuthors(ArrayList<String> authorsList) {
        String authors = "";
        for (String author : authorsList) {
            authors = authors + ", " + author;
        }
        // To remove ',' after last author name
        authors = removeFirstChar(authors);
        return authors;
    }
}
