package com.example.urbanmatch.HomeHelper;

import android.view.View;

import com.example.urbanmatch.R;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderViewHolder extends SliderViewAdapter.ViewHolder {

    SliderView sliderView;

    public SliderViewHolder(View itemView) {
        super(itemView);

        sliderView.findViewById(R.id.imageSlider);
    }
}
