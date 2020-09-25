package com.urbanmatch.HomeUi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.urbanmatch.R;
import com.urbanmatch.UpdateProfileUi.ImageDeleteFragment;
import com.urbanmatch.UpdateProfileUi.ImageUploadActivity;
import com.urbanmatch.UpdateProfileUi.UpdateDetailsFragment;
import com.urbanmatch.UpdateProfileUi.UpdateSelfQualitiesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ImageButton imageButton;
    ImageSlider imageSlider;

    ImageButton editqualities;
    ImageButton deleteImages;

    List<SlideModel> slideModels = new ArrayList<>();

    String uid;

    ImageButton profileEdit;

    TextView profession, education, linkedIn, instagram, qualities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profession = view.findViewById(R.id.profession_holder);
        education = view.findViewById(R.id.education_holder);
        instagram = view.findViewById(R.id.instagram_holder);
        linkedIn = view.findViewById(R.id.linkedin_holder);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        qualities = view.findViewById(R.id.qualities);

        getImages();

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                profession.setText(documentSnapshot.getString("occupation"));
                education.setText(documentSnapshot.getString("education"));
                instagram.setText(documentSnapshot.getString("instagram"));
                linkedIn.setText(documentSnapshot.getString("linkedin"));
            }
        });

        imageButton = view.findViewById(R.id.upload_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageUploadActivity.class);
                startActivity(intent);
            }
        });

        imageSlider = view.findViewById(R.id.imageSlider2);

        profileEdit = view.findViewById(R.id.profile_edit_button);
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDetailsFragment updateDetailsFragment = new UpdateDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("occupation",profession.getText().toString());
                bundle.putString("education",education.getText().toString());
                bundle.putString("instagram",instagram.getText().toString());
                bundle.putString("linkedin",linkedIn.getText().toString());

                updateDetailsFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,updateDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final StringBuffer stringBuffer = new StringBuffer();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("self");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    stringBuffer.append("#"+dataSnapshot.child("quality").getValue(true).toString()+" ");
                }
                qualities.setText(stringBuffer.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editqualities = view.findViewById(R.id.editQualities);
        editqualities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSelfQualitiesFragment updateSelfQualitiesFragment = new UpdateSelfQualitiesFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,updateSelfQualitiesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        deleteImages = view.findViewById(R.id.deleteImages);
        deleteImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDeleteFragment imageDeleteFragment = new ImageDeleteFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, imageDeleteFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void getImages(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slideModels.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                    String imageTitle = "";
                    SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);
                    slideModels.add(slideModel);
                }
                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}