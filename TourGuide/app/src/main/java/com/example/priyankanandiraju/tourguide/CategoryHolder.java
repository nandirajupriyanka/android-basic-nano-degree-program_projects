package com.example.priyankanandiraju.tourguide;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by priyankanandiraju on 11/24/16.
 */
public class CategoryHolder extends RecyclerView.ViewHolder {

    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvLocation;

    public CategoryHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
        tvDate = (TextView) itemView.findViewById(R.id.textView);
    }

    public void setData(Category item) {
        tvTitle.setText(item.getTitle());
        tvLocation.setText(item.getLocation());

        if (item.getDate() == null & !item.hasImage()) {
            tvDate.setVisibility(View.GONE);
        } else {
            tvDate.setVisibility(View.VISIBLE);
            if (item.getDate() != null) {
                tvDate.setText(item.getDate());
                tvDate.setTextColor(Color.RED);
            }
            if (item.hasImage()) {
                tvDate.setBackgroundResource(item.getImage());
            }
        }

    }
}
