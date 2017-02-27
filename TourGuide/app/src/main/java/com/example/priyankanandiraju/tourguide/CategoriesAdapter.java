package com.example.priyankanandiraju.tourguide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by priyankanandiraju on 11/24/16.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryHolder> {


    private final ArrayList<Category> mCategoryArray;

    public CategoriesAdapter(ArrayList<Category> categoryData) {
        mCategoryArray = categoryData;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.setData(mCategoryArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryArray.size();
    }
}
