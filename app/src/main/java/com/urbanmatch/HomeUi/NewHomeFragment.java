package com.urbanmatch.HomeUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.urbanmatch.HomeHelper.UserModel;
import com.urbanmatch.NewHomeHelper.ConnectedModel;
import com.urbanmatch.NewHomeHelper.DepthPageTransformer;
import com.urbanmatch.NewHomeHelper.NewHomeAdapter;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewHomeFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseFirestore db1;
    FirebaseFirestore db2;
    RecyclerView recyclerView;
    ArrayList<UserModel> userModelArrayList;
    ArrayList<ConnectedModel> connectedModelArrayList;

    FirebaseAuth firebaseAuth;

    public String maxAge, minAge, maxHeight, minHeight;
    public ArrayList<Integer> occupation;
    public ArrayList<Integer> relationship;

    public String userCurrentState, userNativeState, userCity, userName, userEducation, userAge, userRelationship, userHeight, userOccupation ;
    String userId;
    String userGender;
    String userGetType;

    String data;

    Map<String,Object> userDetails;

    ViewPager viewPager;

    Boolean subscribed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        userDetails = new HashMap<>();
        userModelArrayList = new ArrayList<>();
        connectedModelArrayList = new ArrayList<>();

        viewPager = view.findViewById(R.id.homePager);
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        getUserData();

        FirebaseFirestore db3;
        db3 = FirebaseFirestore.getInstance();

        return view;
    }

    public void getUserData(){
        db1 = FirebaseFirestore.getInstance();
        db1.collection("userData").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    userCurrentState = documentSnapshot.getString("currentState").trim();
                    userNativeState = documentSnapshot.getString("nativeState").trim();
                    userCity = documentSnapshot.getString("city").trim();
                    userGender = documentSnapshot.getString("gender").trim();
                    userName = documentSnapshot.getString("name").trim();
                    userAge = documentSnapshot.getString("age").trim();
                    userEducation = documentSnapshot.getString("education").trim();
                    userRelationship = documentSnapshot.getString("relationship").trim();
                    userHeight = documentSnapshot.getString("height").trim();
                    userOccupation = documentSnapshot.getString("occupation").trim();
                    subscribed = documentSnapshot.getBoolean("subscription");

                    userDetails.put("name",userName);
                    userDetails.put("age",userAge);
                    userDetails.put("education",userEducation);
                    userDetails.put("relationship",userRelationship);
                    userDetails.put("height",userHeight);
                    userDetails.put("currentState",userCurrentState);
                    userDetails.put("nativeState",userNativeState);
                    userDetails.put("city",userCity);
                    userDetails.put("occupation",userOccupation);
                    userDetails.put("uid",userId);
                    userDetails.put("subscription",subscribed);

                    Log.i("userGender",userGender);

                    if(userGender.equals("1")){
                        data = "femaleUserData";
                    }
                    else if (userGender.equals("2")){
                        data = "maleUserData";
                    }

                    getUserRequirementData();

                }
            }
        });


    }

    public void getUserRequirementData(){
        db2 = FirebaseFirestore.getInstance();
        db2.collection("userRequirementData").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    maxAge = documentSnapshot.getString("maxAge");
                    minAge = documentSnapshot.getString("minAge");
                    maxHeight = documentSnapshot.getString("maxHeight");
                    minHeight = documentSnapshot.getString("minHeight");
                    occupation = (ArrayList<Integer>) documentSnapshot.get("occupation");
                    relationship = (ArrayList<Integer>) documentSnapshot.get("relationship");

                    Log.i("requirements","successful");

                    getConnections();
                }
            }
        });

    }

    public void getConnections(){
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("AllConnections");

        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("AllConnections").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size() != 0){
                    connectedModelArrayList.clear();
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        ConnectedModel connectedModel = new ConnectedModel();
                        connectedModel.setOppositeUserId(documentSnapshot.getString("OppositeUserId"));
                        connectedModel.setUserId(documentSnapshot.getString("userId"));

                        connectedModelArrayList.add(connectedModel);

                    }
                    Log.i("connections",String.valueOf(connectedModelArrayList.size()));
                    getRecommendations();
                }
                else {
                    getRecommendations();
                }
            }
        });
    }

    public void getRecommendations() {

        Log.i("method","entered");

        db = FirebaseFirestore.getInstance();
        db.collection(data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().size() != 0){
                            Log.i("task","entered");
                            userModelArrayList.clear();
                            for (final DocumentSnapshot querySnapShot : task.getResult()) {
                                String rcity = querySnapShot.getString("city").trim();
                                String rcurrentState = querySnapShot.getString("currentState").trim();
                                String rdob = querySnapShot.getString("dob").trim();
                                String reducation = querySnapShot.getString("education").trim();
                                String rgender = querySnapShot.getString("gender").trim();
                                String rheight = querySnapShot.getString("height").trim();
                                String rname = querySnapShot.getString("name").trim();
                                String rlastName = querySnapShot.getString("lastName").trim();
                                String rnativeState = querySnapShot.getString("nativeState").trim();
                                String roccupation = querySnapShot.getString("occupation").trim();
                                String rrelationship = querySnapShot.getString("relationship").trim();
                                String uid  = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String oppositeUserId = querySnapShot.getString("userId").trim();

                                int score1, score2, score3, score4, score5, score6, score7;

                                if (rcurrentState.equals(userCurrentState)) {
                                    score1 = 100;
                                } else {
                                    score1 = 0;
                                }

                                if (rnativeState.equals(userNativeState)) {
                                    score2 = 100;
                                } else {
                                    score2 = 0;
                                }

                                if (rcity.equals(userCity)) {
                                    score3 = 100;
                                } else {
                                    score3 = 0;
                                }

                                int finalScore = score1 + score2 + score3;

                                Log.i("score1", String.valueOf(score1));
                                Log.i("score2", String.valueOf(score2));
                                Log.i("score3", String.valueOf(score3));

                                String fScore = String.valueOf(finalScore);

                                final UserModel userModel = new UserModel(
                                        querySnapShot.getString("city"),
                                        querySnapShot.getString("currentState"),
                                        querySnapShot.getString("dob"),
                                        querySnapShot.getString("age"),
                                        querySnapShot.getString("education"),
                                        querySnapShot.getString("gender"),
                                        querySnapShot.getString("height"),
                                        querySnapShot.getString("name"),
                                        querySnapShot.getString("lastName"),
                                        querySnapShot.getString("nativeState"),
                                        querySnapShot.getString("occupation"),
                                        querySnapShot.getString("relationship"),
                                        querySnapShot.getString("userId"),
                                        finalScore,
                                        querySnapShot.getString("instagram"),
                                        querySnapShot.getString("linkedin")
                                );

                                Log.i("instagram",querySnapShot.getString("instagram"));
                                
                                if(connectedModelArrayList.size()==0){
                                    userModelArrayList.add(userModel);
                                }
                                else {
                                    Log.i("loop2","entered");
                                    for(int i = 0; i<connectedModelArrayList.size(); i++){
                                        if(!connectedModelArrayList.get(i).getOppositeUserId().equals(querySnapShot.getString("userId"))){
                                            userModelArrayList.add(userModel);
                                            break;
                                        }
                                    }
                                }
                            }

                            NewHomeAdapter newHomeAdapter = new NewHomeAdapter(getContext(),userModelArrayList,userDetails,subscribed);
                            viewPager.setAdapter(newHomeAdapter);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}