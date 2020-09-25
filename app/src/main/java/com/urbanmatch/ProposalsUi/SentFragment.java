package com.urbanmatch.ProposalsUi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbanmatch.ProposalsUi.ProposalAdapter.SentRequestAdapter;
import com.urbanmatch.ProposalsUi.ProposalModels.SentRequestModel;
import com.urbanmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

        db.collection("userData").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                getSentRequests();
            }
        });
        return view;
    }

    private void getSentRequests() {
        Log.i("userId",uid);
        db.collection("userData").document(uid).collection("SentRequests").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    sentRequestModelArrayList.clear();
                    for(DocumentSnapshot documentSnapshot: documentSnapshots){
                        final SentRequestModel sentRequestModel = new SentRequestModel();
                        sentRequestModel.setName(documentSnapshot.getString("name"));
                        sentRequestModel.setState(documentSnapshot.getString("state"));
                        sentRequestModel.setRelationship(documentSnapshot.getString("relationship"));
                        sentRequestModel.setProfession(documentSnapshot.getString("profession"));
                        sentRequestModel.setEducation(documentSnapshot.getString("education"));
                        sentRequestModel.setCity(documentSnapshot.getString("city"));
                        sentRequestModel.setHeight(documentSnapshot.getString("height"));
                        sentRequestModel.setAge(documentSnapshot.getString("age"));
                        sentRequestModel.setOppositeUserId(documentSnapshot.getString("oppositeUserId"));

                        sentRequestModelArrayList.add(sentRequestModel);

                    }
                    Log.i("sent size", String.valueOf(sentRequestModelArrayList.size()));

                    SentRequestAdapter sentRequestAdapter = new SentRequestAdapter(getContext(),sentRequestModelArrayList);
                    recyclerView.setAdapter(sentRequestAdapter);
                }
                else {
                    Log.i("sent size", "zero");

                }
            }
        });
    }

}