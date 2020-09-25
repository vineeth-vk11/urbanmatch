package com.urbanmatch.HomeHelper;

import android.content.Context;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderViewAdapter.ViewHolder> {

    private Context context;
    private List<SliderItemModel> sliderItemModelList;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 0;
    }
}
