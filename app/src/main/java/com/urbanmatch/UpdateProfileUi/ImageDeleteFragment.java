package com.urbanmatch.UpdateProfileUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbanmatch.R;
import com.urbanmatch.UpdateProfileUi.Adapters.DeleteImageAdapter;
import com.urbanmatch.UpdateProfileUi.Models.ImageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ImageDeleteFragment extends Fragment {

    FirebaseDatabase db;
    RecyclerView recyclerView;
    ArrayList<ImageModel> imageModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_delete, container, false);

        db = FirebaseDatabase.getInstance();
        imageModelArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.deleteImagesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db.getReference().child("photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getImages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  view;
    }

    private void getImages(){
        db.getReference().child("photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ImageModel imageModel = new ImageModel();
                    imageModel.setImage(dataSnapshot.child("imagePath").getValue().toString());
                    imageModel.setImageKey(dataSnapshot.getKey());

                    imageModelArrayList.add(imageModel);
                }

                DeleteImageAdapter deleteImageAdapter = new DeleteImageAdapter(getContext(),imageModelArrayList);
                recyclerView.setAdapter(deleteImageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}