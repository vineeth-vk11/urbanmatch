package com.example.urbanmatch.ProposalsUi.ProposalViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.urbanmatch.R;

public class NewProposalViewHolder extends RecyclerView.ViewHolder {
    public TextView name, age, height, location, occupation, education;
    public Button accept, decline;
    public ImageSlider imageSlider;

    public NewProposalViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        age = itemView.findViewById(R.id.age);
        height = itemView.findViewById(R.id.height);
        location = itemView.findViewById(R.id.location);
        occupation = itemView.findViewById(R.id.occupation);
        education = itemView.findViewById(R.id.education);

        accept = itemView.findViewById(R.id.accept);
        decline = itemView.findViewById(R.id.decline);

        imageSlider = itemView.findViewById(R.id.proposal_slider);
    }
}
