package com.example.priyankanandiraju.tourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by priyankanandiraju on 11/24/16.
 */

class MainPagerAdapter extends FragmentPagerAdapter {

    private final String[] categoryArray;

    MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        categoryArray = context.getResources().getStringArray(R.array.category);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(categoryArray[position]);
    }

    @Override
    public int getCount() {
        return categoryArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryArray[position];
    }
}
