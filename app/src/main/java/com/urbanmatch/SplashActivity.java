package com.urbanmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.urbanmatch.LoginHelper.LoginMobileNumberActivity;
import com.urbanmatch.profileUi.Profile1Activity;
import com.urbanmatch.profileUi.Profile3Activity;
import com.urbanmatch.profileUi.Profile4Activity;
import com.urbanmatch.profileUi.QualitiesActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser == null){
            Intent intent = new Intent(SplashActivity.this, LoginMobileNumberActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            FirebaseFirestore db;
            db = FirebaseFirestore.getInstance();
            db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if(! documentSnapshot.exists()){
                        Intent intent = new Intent(SplashActivity.this, Profile1Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        String profileStatus = documentSnapshot.getString("profileStatus");

                        if(profileStatus.equals("1")){
                            Intent intent = new Intent(SplashActivity.this, QualitiesActivity.class);
                            intent.putExtra("type","self");
                            startActivity(intent);
                            finish();
                        }
                        else if(profileStatus.equals("2")){
                            Intent intent = new Intent(SplashActivity.this, Profile3Activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(profileStatus.equals("3")){
                            Intent intent = new Intent(SplashActivity.this,QualitiesActivity.class);
                            intent.putExtra("type","required");
                            startActivity(intent);
                            finish();
                        }
                        else if(profileStatus.equals("4")){
                            Intent intent = new Intent(SplashActivity.this, Profile4Activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(profileStatus.equals("5")){
                            Intent intent= new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }
    }
}