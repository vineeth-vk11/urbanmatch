package com.urbanmatch.ChatUi.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.urbanmatch.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public CardView cardView;
    public CircleImageView profileIcon;
    public TextView lastMessage;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        cardView = itemView.findViewById(R.id.chat_card);
        profileIcon = itemView.findViewById(R.id.profileIcon);
        lastMessage = itemView.findViewById(R.id.lastMessage);
    }
}
