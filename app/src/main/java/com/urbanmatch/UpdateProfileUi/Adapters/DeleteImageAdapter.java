package com.urbanmatch.UpdateProfileUi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urbanmatch.R;
import com.urbanmatch.UpdateProfileUi.Models.ImageModel;
import com.urbanmatch.UpdateProfileUi.ViewHolders.DeleteImageViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeleteImageAdapter extends RecyclerView.Adapter<DeleteImageViewHolder> {

    Context context;
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();

    public DeleteImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }

    @NonNull
    @Override
    public DeleteImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_delete_image,parent,false);
        return new DeleteImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteImageViewHolder holder, final int position) {
        Picasso.get().load(imageModelArrayList.get(position).getImage().toString()).into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageModelArrayList.size() == 1){
                    Toast.makeText(context,"You should have atleast one image",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReferenceFromUrl(imageModelArrayList.get(position).getImage());
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FirebaseDatabase db;
                            db = FirebaseDatabase.getInstance();
                            db.getReference().child("photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(imageModelArrayList.get(position).getImageKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Image Deleted",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }
}
