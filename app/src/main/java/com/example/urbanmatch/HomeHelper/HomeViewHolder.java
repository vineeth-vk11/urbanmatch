package com.example.urbanmatch.HomeHelper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.urbanmatch.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    public TextView nameAgeRelationship, profession, state, city, height, score;
    public ImageButton connect;
    public ImageSlider imageSlider;
    public TextView qualities;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        imageSlider = itemView.findViewById(R.id.imageSlider);
        nameAgeRelationship = itemView.findViewById(R.id.textView5);
        height = itemView.findViewById(R.id.textView6);
        profession = itemView.findViewById(R.id.textView7);
        state = itemView.findViewById(R.id.textView8);
        city = itemView.findViewById(R.id.textView9);
        score = itemView.findViewById(R.id.textView10);
        connect = itemView.findViewById(R.id.connect);
        qualities = itemView.findViewById(R.id.qualities);
    }
}
