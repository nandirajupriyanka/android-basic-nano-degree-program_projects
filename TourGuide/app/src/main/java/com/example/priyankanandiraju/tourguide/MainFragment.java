package com.example.priyankanandiraju.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by priyankanandiraju on 11/24/16.
 */

public class MainFragment extends Fragment {

    private static final String CATEGORY = "CATEGORY";

    public static MainFragment newInstance(String category) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        CategoriesAdapter adapter = new CategoriesAdapter(getCategoryData());
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<Category> getCategoryData() {
        String category = getArguments().getString(CATEGORY);
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        if (category != null) {
            switch (category) {
                case "PLACES":
                    categoryArrayList.add(new Category(getString(R.string.reunion_tower), getString(R.string.reunion_loc), null, R.drawable.reunion));
                    categoryArrayList.add(new Category(getString(R.string.pioneer), getString(R.string.pioneer_loc), null, R.drawable.pioneer));
                    categoryArrayList.add(new Category(getString(R.string.sixth_floor), getString(R.string.sixth_floor_loc), null, R.drawable.sixth_floor));
                    categoryArrayList.add(new Category(getString(R.string.dragon_park), getString(R.string.dragon_park_loc), null, R.drawable.dragon_park));
                    break;
                case "FOOD":
                    categoryArrayList.add(new Category(getString(R.string.food_1), getString(R.string.food_1_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.food_2), getString(R.string.food_2_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.food_3), getString(R.string.food_3_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.food_4), getString(R.string.food_4_loc), null));
                    break;
                case "EVENTS":
                    categoryArrayList.add(new Category(getString(R.string.event_1), getString(R.string.event_1_loc), getString(R.string.nov_3)));
                    categoryArrayList.add(new Category(getString(R.string.event_2), getString(R.string.event_2_loc), getString(R.string.nov_3)));
                    categoryArrayList.add(new Category(getString(R.string.event_3), getString(R.string.evenmt_3_loc), getString(R.string.nov_4)));
                    break;
                case "SHOP":
                    categoryArrayList.add(new Category(getString(R.string.shop_1), getString(R.string.shop_1_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.shop_2), getString(R.string.shop_2_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.shop_3), getString(R.string.shop_3_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.shop_4), getString(R.string.shop_4_loc), null));
                    break;
                case "COFFEE":
                    categoryArrayList.add(new Category(getString(R.string.coffee_1), getString(R.string.coffee_1_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.coffee_2), getString(R.string.coffee_2_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.coffee_3), getString(R.string.coffee_3_loc), null));
                    categoryArrayList.add(new Category(getString(R.string.coffee_4), getString(R.string.coffee_4_loc), null));
                    break;
                default:
                    break;
            }
            return categoryArrayList;
        }
        return categoryArrayList;
    }
}
