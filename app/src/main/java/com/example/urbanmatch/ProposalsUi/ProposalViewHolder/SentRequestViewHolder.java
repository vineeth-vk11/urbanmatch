package com.example.urbanmatch.ProposalsUi.ProposalViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanmatch.R;

public class SentRequestViewHolder extends RecyclerView.ViewHolder {

    public TextView sentName, sentLocation, sentOccupation, sentEducation, sentAge, sentHeight;

    public SentRequestViewHolder(@NonNull View itemView) {
        super(itemView);

        sentName = itemView.findViewById(R.id.name);
        sentLocation = itemView.findViewById(R.id.location);
        sentOccupation = itemView.findViewById(R.id.occupation);
        sentEducation = itemView.findViewById(R.id.education);
        sentAge = itemView.findViewById(R.id.age);
        sentHeight = itemView.findViewById(R.id.height);

    }
}
