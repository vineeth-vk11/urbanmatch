package com.urbanmatch.ProposalsUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbanmatch.ProposalsUi.ProposalAdapter.NewRequestAdapter;
import com.urbanmatch.ProposalsUi.ProposalModels.NewRequestModel;
import com.urbanmatch.R;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NewRequestModel> newRequestModelArrayList;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    String uid;

    Map<String,Object> userDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        newRequestModelArrayList = new ArrayList<>();
        userDetails = new HashMap<>();
        recyclerView = view.findViewById(R.id.new_request_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        db = FirebaseFirestore.getInstance();
        getUserData();
        db.collection("userData").document(uid).collection("ConnectionRequests").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                getRequests();
            }
        });
        return view;
    }

    private void getUserData(){
        db.collection("userData").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    userDetails.put("name",documentSnapshot.getString("name"));
                    userDetails.put("age",documentSnapshot.getString("age"));
                    userDetails.put("currentState",documentSnapshot.getString("currentState"));
                    userDetails.put("nativeState",documentSnapshot.getString("nativeState"));
                    userDetails.put("city",documentSnapshot.getString("city"));
                    userDetails.put("education",documentSnapshot.getString("education"));
                    userDetails.put("occupation",documentSnapshot.getString("occupation"));
                    userDetails.put("height",documentSnapshot.getString("height"));
                    userDetails.put("relationship",documentSnapshot.getString("relationship"));
                    userDetails.put("uid",uid);
                }
            }
        });

        getRequests();
    }

    private void getRequests(){
        db = FirebaseFirestore.getInstance();
        db.collection("userData").document(uid).collection("ConnectionRequests").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    newRequestModelArrayList.clear();
                    for(DocumentSnapshot querySnapshot: documentSnapshots){
                        final NewRequestModel newRequestModel = new NewRequestModel();
                        newRequestModel.setName(querySnapshot.getString("name"));
                        newRequestModel.setAge(querySnapshot.getString("age"));
                        newRequestModel.setCity(querySnapshot.getString("city"));
                        newRequestModel.setCurrentState(querySnapshot.getString("currentState"));
                        newRequestModel.setEducation(querySnapshot.getString("education"));
                        newRequestModel.setHeight(querySnapshot.getString("height"));
                        newRequestModel.setNativeState(querySnapshot.getString("nativeState"));
                        newRequestModel.setOccupation(querySnapshot.getString("occupation"));
                        newRequestModel.setRelationship(querySnapshot.getString("relationship"));
                        newRequestModel.setUid(querySnapshot.getString("uid"));

                        newRequestModelArrayList.add(newRequestModel);

                    }
                    Log.i("request size", String.valueOf(newRequestModelArrayList.size()));
                    NewRequestAdapter newRequestAdapter = new NewRequestAdapter(getContext(),newRequestModelArrayList,userDetails);
                    recyclerView.setAdapter(newRequestAdapter);
                }
                else {

                }
            }
        });
//        db.collection("userData").document(uid).collection("ConnectionRequests").
//                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                newRequestModelArrayList.clear();
//                for(DocumentSnapshot querySnapshot: task.getResult()){
//                    NewRequestModel newRequestModel = new NewRequestModel();
//                    newRequestModel.setName(querySnapshot.getString("name"));
//                    newRequestModel.setAge(querySnapshot.getString("age"));
//                    newRequestModel.setCity(querySnapshot.getString("city"));
//                    newRequestModel.setCurrentState(querySnapshot.getString("currentState"));
//                    newRequestModel.setEducation(querySnapshot.getString("education"));
//                    newRequestModel.setHeight(querySnapshot.getString("height"));
//                    newRequestModel.setNativeState(querySnapshot.getString("nativeState"));
//                    newRequestModel.setOccupation(querySnapshot.getString("occupation"));
//                    newRequestModel.setRelationship(querySnapshot.getString("relationship"));
//                    newRequestModel.setUid(querySnapshot.getString("uid"));
//
//                    newRequestModelArrayList.add(newRequestModel);
//                }
//                NewRequestAdapter newRequestAdapter = new NewRequestAdapter(getContext(),newRequestModelArrayList,userDetails);
//                recyclerView.setAdapter(newRequestAdapter);
//            }
//        });
    }
}