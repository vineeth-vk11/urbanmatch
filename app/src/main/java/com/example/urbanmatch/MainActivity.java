package com.example.urbanmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.urbanmatch.HomeUi.ChatFragment;
import com.example.urbanmatch.HomeUi.HomeFragment;
import com.example.urbanmatch.HomeUi.MoreFragment;
import com.example.urbanmatch.HomeUi.ProfileFragment;
import com.example.urbanmatch.HomeUi.ProposalsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.MoreExecutors;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.home_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    HomeFragment homeFragment = new HomeFragment();
    ProposalsFragment proposalsFragment = new ProposalsFragment();
    ChatFragment chatFragment = new ChatFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    MoreFragment moreFragment = new MoreFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,homeFragment).commit();
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