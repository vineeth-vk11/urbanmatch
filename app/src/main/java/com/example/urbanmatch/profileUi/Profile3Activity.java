package com.example.urbanmatch.profileUi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.urbanmatch.HomeUi.HomeFragment;
import com.example.urbanmatch.MainActivity;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile3Activity extends AppCompatActivity {

    Button  next;
    FloatingActionButton addQualities;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String gender = intent.getStringExtra("gender");

        String[] MINAGE = new String[]{"21", "22", "23"};
        String[] MAXAGE = new String[]{"25","26","27"};
        String[] OCCUPATION = new String[]{"Occupation1", "Occupation2"};
        String[] RELATION = new String[]{"Relation1", "Relation2"};
        String[] MINHEIGHT = new String[]{"Height1", "Height2"};
        String[] MAXHEIGHT = new String[]{"Height3", "Height3"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MINAGE
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MAXAGE
        );

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                OCCUPATION
        );

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                RELATION
        );

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MINHEIGHT
        );

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MAXHEIGHT
        );

        final AutoCompleteTextView minAges = findViewById(R.id.age_min_dropdown);
        final AutoCompleteTextView maxAges = findViewById(R.id.age_max_dropdown);
        final AutoCompleteTextView occupations = findViewById(R.id.occupation_dropdown);
        final AutoCompleteTextView relationships = findViewById(R.id.relationship_dropdown);
        final AutoCompleteTextView minHeights = findViewById(R.id.height_min_dropdown);
        final AutoCompleteTextView maxHeights = findViewById(R.id.height_max_dropdown);

        minAges.setAdapter(adapter);
        maxAges.setAdapter(adapter2);
        occupations.setAdapter(adapter3);
        relationships.setAdapter(adapter4);
        minHeights.setAdapter(adapter5);
        maxHeights.setAdapter(adapter6);

        next = findViewById(R.id.next2);

        db = FirebaseFirestore.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String minAge = minAges.getText().toString().trim();
                final String maxAge = maxAges.getText().toString().trim();
                final String occupation = occupations.getText().toString().trim();
                final String relationship = relationships.getText().toString().trim();
                final String minHeight = minHeights.getText().toString().trim();
                final String maxHeight = maxHeights.getText().toString().trim();

                if(TextUtils.isEmpty(minAge)){
                    Toast.makeText(Profile3Activity.this,"Please select Minimum Age",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(maxAge)){
                    Toast.makeText(Profile3Activity.this,"Please select Maximum Age",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(occupation)){
                    Toast.makeText(Profile3Activity.this,"Please select Occupation",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(relationship)){
                    Toast.makeText(Profile3Activity.this,"Please select Relationship",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(minHeight)){
                    Toast.makeText(Profile3Activity.this,"Please select Minimum Height",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(maxHeight)){
                    Toast.makeText(Profile3Activity.this,"Please select Maximum Height",Toast.LENGTH_SHORT).show();
                    return;
                }

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("userRequirementData").document(uid);
                Map<String,Object> user = new HashMap<>();
                user.put("minAge",minAge);
                user.put("maxAge",maxAge);
                user.put("occupation",occupation);
                user.put("relationship",relationship);
                user.put("minHeight",minHeight);
                user.put("maxHeight",maxHeight);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(Profile3Activity.this, QualitiesActivity.class);
                        intent.putExtra("type","required");
                        Bundle bundle = new Bundle();
                        bundle.putString("userId",userId);
                        bundle.putString("gender",gender);
                        bundle.putString("minAge",minAge);
                        bundle.putString("maxAge",maxAge);
                        bundle.putString("occupation",occupation);
                        bundle.putString("relationship",relationship);
                        bundle.putString("minHeight",minHeight);
                        bundle.putString("maxHeight",maxHeight);

                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundle);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}