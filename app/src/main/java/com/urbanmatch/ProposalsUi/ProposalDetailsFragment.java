package com.urbanmatch.ProposalsUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ProposalDetailsFragment extends Fragment {

    String userId;

    FirebaseDatabase database;
    FirebaseFirestore db;

    ImageSlider imageSlider;
    final List<SlideModel> slideModels = new ArrayList<>();

    TextView qualities;

    TextView txtName;
    TextView txtProfession;
    TextView txtState;
    TextView txtCity;
    TextView txtHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proposal_details, container, false);

        Bundle bundle = getArguments();
        userId = bundle.getString("userId");

        imageSlider = view.findViewById(R.id.imageSlider);
        qualities = view.findViewById(R.id.qualities);

        txtName = view.findViewById(R.id.textView5);
        txtProfession = view.findViewById(R.id.textView7);
        txtState = view.findViewById(R.id.textView8);
        txtCity = view.findViewById(R.id.textView9);
        txtHeight = view.findViewById(R.id.textView6);

        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

        final StringBuffer stringBuffer = new StringBuffer();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").child(userId)
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

        db.collection("userData").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                txtName.setText(String.format("%s %s, %s, %s", documentSnapshot.getString("name"), documentSnapshot.getString("lastName"), documentSnapshot.getString("age"), documentSnapshot.getString("relationship")));
                txtProfession.setText(documentSnapshot.getString("occupation"));
                txtState.setText("From "+documentSnapshot.getString("nativeState"));
                txtCity.setText(String.format("Lives in %s", documentSnapshot.getString("city")));
                txtHeight.setText(documentSnapshot.getString("height"));

            }
        });
        return view;
    }
}