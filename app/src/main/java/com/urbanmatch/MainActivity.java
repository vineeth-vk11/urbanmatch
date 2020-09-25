package com.urbanmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.urbanmatch.HomeUi.ChatFragment;
import com.urbanmatch.HomeUi.HomeFragment;
import com.urbanmatch.HomeUi.MoreFragment;
import com.urbanmatch.HomeUi.NewHomeFragment;
import com.urbanmatch.HomeUi.ProfileFragment;
import com.urbanmatch.HomeUi.ProposalsFragment;
import com.urbanmatch.profileUi.Profile4Activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.home_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null){
            Log.i("entered","true1");
            Log.i("numbe",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
        else {
            Intent intent = new Intent(MainActivity.this, Profile4Activity.class);
            startActivity(intent);
            finish();
        }

    }

    HomeFragment homeFragment = new HomeFragment();
    NewHomeFragment newHomeFragment = new NewHomeFragment();
    ProposalsFragment proposalsFragment = new ProposalsFragment();
    ChatFragment chatFragment = new ChatFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    MoreFragment moreFragment = new MoreFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,newHomeFragment).commit();
                return true;
            case R.id.proposals:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,proposalsFragment).commit();
                return true;
            case R.id.chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,chatFragment).commit();
                return true;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,profileFragment).commit();
                return true;
            case R.id.more:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,moreFragment).commit();
                return true;
        }
        return false;
    }
}