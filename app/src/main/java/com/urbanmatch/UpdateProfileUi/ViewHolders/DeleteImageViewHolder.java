package com.urbanmatch.UpdateProfileUi.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urbanmatch.R;

public class DeleteImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public Button delete;

    public DeleteImageViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.profilepic);
        delete = itemView.findViewById(R.id.deleteImage);
    }
}
