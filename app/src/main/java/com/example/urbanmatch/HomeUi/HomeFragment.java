package com.example.urbanmatch.HomeUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.urbanmatch.HomeHelper.HomeAdapter;
import com.example.urbanmatch.HomeHelper.UserModel;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseFirestore db1;
    FirebaseFirestore db2;
    RecyclerView recyclerView;
    ArrayList<UserModel> userModelArrayList;
    HomeAdapter homeAdapter;
    FirebaseAuth firebaseAuth;

    CardStackView cardStackView;
    CardStackLayoutManager cardStackLayoutManager;

    public String maxAge, minAge, maxHeight, minHeight, occupation, relationship;
    public String userCurrentState, userNativeState, userCity, userName, userEducation, userAge, userRelationship, userHeight, userOccupation ;
    String userId;
    String userGender;
    String userGetType;

    String data;

    Map<String,Object> userDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        cardStackView = view.findViewById(R.id.home_card_stack);

        userDetails = new HashMap<>();
        userModelArrayList = new ArrayList<>();
        getUserData();

        return view;
    }

    private void setupCards(){
        cardStackLayoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                if(direction == Direction.Top){

                }
                if(direction == Direction.Bottom){

                }

                if(cardStackLayoutManager.getTopPosition() == homeAdapter.getItemCount()/2){
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });

        cardStackLayoutManager.setStackFrom(StackFrom.None);
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setTranslationInterval(8.0f);
        cardStackLayoutManager.setScaleInterval(0.95f);
        cardStackLayoutManager.setSwipeThreshold(0.2f);
        cardStackLayoutManager.setMaxDegree(20.0f);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollHorizontal(false);
        cardStackLayoutManager.setCanScrollVertical(true);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Manual);
        cardStackLayoutManager.setOverlayInterpolator(new LinearInterpolator());
        homeAdapter = new HomeAdapter(getContext(),userModelArrayList,userDetails);
        cardStackView.setAdapter(homeAdapter);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        ArrayList<UserModel> oldUserArrayList = homeAdapter.getUserModelArrayList();
        ArrayList<UserModel> newUserArrayList = new ArrayList<>(getRecommendations());
        CardStackCallBack cardStackCallBack = new CardStackCallBack(oldUserArrayList,newUserArrayList);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(cardStackCallBack);
        homeAdapter.setUserModelArrayList(newUserArrayList);
        hasil.dispatchUpdatesTo(homeAdapter);
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

                    Log.i("userGender",userGender);

                    if(userGender.equals("1")){
                        data = "femaleUserData";
                    }
                    else if (userGender.equals("2")){
                        data = "maleUserData";
                    }
                }

                getUserRequirementData();
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
                    occupation = documentSnapshot.getString("occupation").trim();
                    relationship = documentSnapshot.getString("relationship").trim();

                    Log.i("maxAge",maxAge);
                    Log.i("minAge",minAge);
                    Log.i("maxHeight",maxHeight);
                    Log.i("minHeight",minHeight);
                    Log.i("occupation",occupation);
                    Log.i("relationship",relationship);
                }
            }
        });
        getRecommendations();
    }

    public ArrayList<UserModel> getRecommendations() {
        db = FirebaseFirestore.getInstance();
        db.collection(data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapShot : task.getResult()) {

                            String rcity = querySnapShot.getString("city").trim();
                            String rcurrentState = querySnapShot.getString("currentState").trim();
                            String rdob = querySnapShot.getString("dob").trim();
                            String reducation = querySnapShot.getString("education").trim();
                            String rgender = querySnapShot.getString("gender").trim();
                            String rheight = querySnapShot.getString("height").trim();
                            String rname = querySnapShot.getString("name").trim();
                            String rnativeState = querySnapShot.getString("nativeState").trim();
                            String roccupation = querySnapShot.getString("occupation").trim();
                            String rrelationship = querySnapShot.getString("relationship").trim();

                            Log.i("rcity", rcity);
                            Log.i("rcurrentState", rcurrentState);
                            Log.i("rdob", rdob);
                            Log.i("reducation", reducation);
                            Log.i("rgender", rgender);
                            Log.i("rheight", rheight);
                            Log.i("rname", rname);
                            Log.i("rnativeState", rnativeState);
                            Log.i("roccupation", roccupation);
                            Log.i("rrelationship", rrelationship);

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

                            if (roccupation.equals(occupation)) {
                                score4 = 50;
                            } else {
                                score4 = 0;
                            }

                            if (rrelationship.equals(relationship)) {
                                score5 = 40;
                            } else {
                                score5 = 0;
                            }
                            int finalScore = score1 + score2 + score3 + score4 + score5;

                            Log.i("score1", String.valueOf(score1));
                            Log.i("score2", String.valueOf(score2));
                            Log.i("score3", String.valueOf(score3));
                            Log.i("score4", String.valueOf(score4));
                            Log.i("score5", String.valueOf(score5));

                            String fScore = String.valueOf(finalScore);

                            UserModel userModel = new UserModel(
                                    querySnapShot.getString("city"),
                                    querySnapShot.getString("currentState"),
                                    querySnapShot.getString("dob"),
                                    querySnapShot.getString("age"),
                                    querySnapShot.getString("education"),
                                    querySnapShot.getString("gender"),
                                    querySnapShot.getString("height"),
                                    querySnapShot.getString("name"),
                                    querySnapShot.getString("nativeState"),
                                    querySnapShot.getString("occupation"),
                                    querySnapShot.getString("relationship"),
                                    finalScore,
                                    querySnapShot.getString("userId"));

                            userModelArrayList.add(userModel);

                        }
                        getRecommendations1();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        return userModelArrayList;

    }

    public ArrayList<UserModel> getRecommendations1() {
        db = FirebaseFirestore.getInstance();
        db.collection(data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapShot : task.getResult()) {

                            String rcity = querySnapShot.getString("city").trim();
                            String rcurrentState = querySnapShot.getString("currentState").trim();
                            String rdob = querySnapShot.getString("dob").trim();
                            String reducation = querySnapShot.getString("education").trim();
                            String rgender = querySnapShot.getString("gender").trim();
                            String rheight = querySnapShot.getString("height").trim();
                            String rname = querySnapShot.getString("name").trim();
                            String rnativeState = querySnapShot.getString("nativeState").trim();
                            String roccupation = querySnapShot.getString("occupation").trim();
                            String rrelationship = querySnapShot.getString("relationship").trim();

                            Log.i("rcity", rcity);
                            Log.i("rcurrentState", rcurrentState);
                            Log.i("rdob", rdob);
                            Log.i("reducation", reducation);
                            Log.i("rgender", rgender);
                            Log.i("rheight", rheight);
                            Log.i("rname", rname);
                            Log.i("rnativeState", rnativeState);
                            Log.i("roccupation", roccupation);
                            Log.i("rrelationship", rrelationship);

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

                            if (roccupation.equals(occupation)) {
                                score4 = 50;
                            } else {
                                score4 = 0;
                            }

                            if (rrelationship.equals(relationship)) {
                                score5 = 40;
                            } else {
                                score5 = 0;
                            }
                            int finalScore = score1 + score2 + score3 + score4 + score5;

                            Log.i("score1", String.valueOf(score1));
                            Log.i("score2", String.valueOf(score2));
                            Log.i("score3", String.valueOf(score3));
                            Log.i("score4", String.valueOf(score4));
                            Log.i("score5", String.valueOf(score5));

                            String fScore = String.valueOf(finalScore);

                            UserModel userModel = new UserModel(
                                    querySnapShot.getString("city"),
                                    querySnapShot.getString("currentState"),
                                    querySnapShot.getString("dob"),
                                    querySnapShot.getString("age"),
                                    querySnapShot.getString("education"),
                                    querySnapShot.getString("gender"),
                                    querySnapShot.getString("height"),
                                    querySnapShot.getString("name"),
                                    querySnapShot.getString("nativeState"),
                                    querySnapShot.getString("occupation"),
                                    querySnapShot.getString("relationship"),
                                    finalScore,
                                    querySnapShot.getString("userId"));

                            userModelArrayList.add(userModel);

                        }
                        setupCards();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        return userModelArrayList;

    }

}