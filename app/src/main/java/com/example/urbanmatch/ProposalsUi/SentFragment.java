package com.example.urbanmatch.ProposalsUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urbanmatch.ProposalsUi.ProposalAdapter.SentRequestAdapter;
import com.example.urbanmatch.ProposalsUi.ProposalModels.SentRequestModel;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SentFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    String uid;
    ArrayList<SentRequestModel> sentRequestModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent, container, false);

        recyclerView = view.findViewById(R.id.sent_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        db = FirebaseFirestore.getInstance();

        sentRequestModelArrayList = new ArrayList<>();

        getSentRequests();

        db.collection("userData").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                getSentRequests();
            }
        });
        return view;
    }

    private void getSentRequests() {
        db.collection("userData").document(uid).collection("SentRequests").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        sentRequestModelArrayList.clear();
                        for(DocumentSnapshot querySnapshot: task.getResult()){
                            SentRequestModel sentRequestModel = new SentRequestModel();
                            sentRequestModel.setName(querySnapshot.getString("name"));
                            sentRequestModel.setAge(querySnapshot.getString("age"));
                            sentRequestModel.setHeight(querySnapshot.getString("height"));
                            sentRequestModel.setCity(querySnapshot.getString("city"));
                            sentRequestModel.setEducation(querySnapshot.getString("education"));
                            sentRequestModel.setOppositeUserId(querySnapshot.getString("oppositeUserId"));
                            sentRequestModel.setProfession(querySnapshot.getString("profession"));
                            sentRequestModel.setRelationship(querySnapshot.getString("relationship"));
                            sentRequestModel.setState(querySnapshot.getString("state"));

                            sentRequestModelArrayList.add(sentRequestModel);
                            SentRequestAdapter sentRequestAdapter = new SentRequestAdapter(getContext(),sentRequestModelArrayList);
                            recyclerView.setAdapter(sentRequestAdapter);

                        }
                    }
                });
    }

}