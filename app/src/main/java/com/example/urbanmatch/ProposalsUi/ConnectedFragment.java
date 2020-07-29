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
import android.widget.Toast;

import com.example.urbanmatch.ProposalsUi.ProposalAdapter.SentRequestAdapter;
import com.example.urbanmatch.ProposalsUi.ProposalModels.SentRequestModel;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ConnectedFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    String uid;

    ArrayList<SentRequestModel> sentRequestModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connected, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        db = FirebaseFirestore.getInstance();
        sentRequestModelArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.accepted_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getAcceptedRequests();

        db.collection("userData").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                getAcceptedRequests();
            }
        });
        return view;
    }

    private void getAcceptedRequests() {
        CollectionReference collectionReference = db.collection("userData").document(uid).collection("Accepted");
        if(collectionReference == null){
            Toast.makeText(getContext(),"No connections",Toast.LENGTH_SHORT).show();
        }
        else if(collectionReference != null){
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    sentRequestModelArrayList.clear();

                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        SentRequestModel sentRequestModel = new SentRequestModel();
                        sentRequestModel.setName(documentSnapshot.getString("name"));
                        sentRequestModel.setState(documentSnapshot.getString("state"));
                        sentRequestModel.setRelationship(documentSnapshot.getString("relationship"));
                        sentRequestModel.setProfession(documentSnapshot.getString("occupation"));
                        sentRequestModel.setOppositeUserId(documentSnapshot.getString(uid));
                        sentRequestModel.setEducation(documentSnapshot.getString("education"));
                        sentRequestModel.setCity(documentSnapshot.getString("city"));
                        sentRequestModel.setHeight(documentSnapshot.getString("height"));
                        sentRequestModel.setAge(documentSnapshot.getString("age"));

                        sentRequestModelArrayList.add(sentRequestModel);
                        SentRequestAdapter sentRequestAdapter = new SentRequestAdapter(getContext(),sentRequestModelArrayList);
                        recyclerView.setAdapter(sentRequestAdapter);
                    }
                }
            });
        }

    }
}