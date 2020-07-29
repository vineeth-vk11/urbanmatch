package com.example.urbanmatch.ChatUi.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanmatch.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public CardView cardView;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        cardView = itemView.findViewById(R.id.chat_card);
    }
}
