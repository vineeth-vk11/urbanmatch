package com.urbanmatch.ProposalsUi.ProposalViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.urbanmatch.R;

public class SentRequestViewHolder extends RecyclerView.ViewHolder {

    public TextView sentName, sentLocation, sentOccupation, sentEducation, sentAge, sentHeight;
    public ImageSlider imageSlider;
    public Button cancel;

    public CardView connectedCard;

    public SentRequestViewHolder(@NonNull View itemView) {
        super(itemView);

        sentName = itemView.findViewById(R.id.name);
        sentLocation = itemView.findViewById(R.id.location);
        sentOccupation = itemView.findViewById(R.id.occupation);
        sentEducation = itemView.findViewById(R.id.education);
        sentAge = itemView.findViewById(R.id.age);
        sentHeight = itemView.findViewById(R.id.height);

        imageSlider = itemView.findViewById(R.id.proposal_slider);

        cancel = itemView.findViewById(R.id.cancel);

        connectedCard = itemView.findViewById(R.id.connected_card);

    }
}
