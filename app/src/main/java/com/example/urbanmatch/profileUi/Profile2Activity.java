package com.example.urbanmatch.profileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.urbanmatch.MainActivity;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile2Activity extends AppCompatActivity {

    Button button;
    FloatingActionButton floatingActionButton;
    FirebaseFirestore db;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        progressBar = findViewById(R.id.progressBar);

        Intent intent1 = getIntent();
        final String userId = intent1.getStringExtra("userId");
        final String name = intent1.getStringExtra("name");
        final String dob = intent1.getStringExtra("dob");
        final String gender = intent1.getStringExtra("gender");
        final String nativeState = intent1.getStringExtra("nativeState");
        final String currentState = intent1.getStringExtra("currentState");
        final String city = intent1.getStringExtra("city");
        final String age = intent1.getStringExtra("age");
        final Uri uri = Uri.parse(intent1.getStringExtra("imageUri"));

        final String fuserId = userId;
        final String fname = name;
        final String fdob = dob;
        final String fgender = gender;
        final String fnativeState = nativeState;
        final String fcurrentState = currentState;
        final String fcity = city;
        final String fage = age;
        final Uri furi = uri;

        String[] EDUCATION = new String[]{"B.Arch", "B.Des", "B.E/B.TECH", "B.Pharma", "M.Arch", "M.Des", "M.E/M.TECH", "M.Pharma", "M.S(Engineering)"};
        String[] OCCUPATION = new String[]{"Occupation1", "Occupation2"};
        String[] RELATION = new String[]{"Relation1", "Relation2"};
        String[] HEIGHT = new String[]{"Height1", "Height2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                EDUCATION
        );

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                OCCUPATION
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                RELATION
        );

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                HEIGHT
        );

        final AutoCompleteTextView educations = findViewById(R.id.education_dropdown);
        final AutoCompleteTextView occupations = findViewById(R.id.occupation_dropdown);
        final AutoCompleteTextView relationships = findViewById(R.id.relationship_dropdown);
        final AutoCompleteTextView heights = findViewById(R.id.height_dropdown);

        educations.setAdapter(adapter);
        occupations.setAdapter(adapter1);
        relationships.setAdapter(adapter2);
        heights.setAdapter(adapter3);

        db = FirebaseFirestore.getInstance();

        button = findViewById(R.id.next1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String education = educations.getText().toString().trim();
                String occupation = occupations.getText().toString().trim();
                String relationship = relationships.getText().toString().trim();
                String height = heights.getText().toString().trim();

                if(TextUtils.isEmpty(education)){
                    Toast.makeText(Profile2Activity.this,"Please select your education",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(occupation)){
                    Toast.makeText(Profile2Activity.this,"Please select your occupation ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(relationship)){
                    Toast.makeText(Profile2Activity.this,"Please select your relationship",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(height)){
                    Toast.makeText(Profile2Activity.this,"Please select your height",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    button.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                Log.i("userId",fuserId);
                Log.i("name",fname);
                Log.i("dob",fdob);
                Log.i("gender",fgender);
                Log.i("nativeState",fnativeState);
                Log.i("currentState",fcurrentState);
                Log.i("City",fcity);
                Log.i("education",education);
                Log.i("occupation",occupation);
                Log.i("relationship",relationship);
                Log.i("height",height);

                final DocumentReference documentReference = db.collection("femaleUserData").document(userId);
                final DocumentReference documentReference1 = db.collection("maleUserData").document(userId);
                final DocumentReference documentReference2 = db.collection("userData").document(userId);

                final Map<String,Object> user = new HashMap<>();
                user.put("name",name);
                user.put("dob",dob);
                user.put("gender",gender);
                user.put("nativeState",nativeState);
                user.put("currentState",currentState);
                user.put("city",city);
                user.put("education",education);
                user.put("occupation",occupation);
                user.put("relationship",relationship);
                user.put("height",height);
                user.put("age",age);
                user.put("userId",userId);
                user.put("instagram","");
                user.put("linkedin","");

                final StorageReference storageReference1 = storageReference.child(String.valueOf(name)+"/" + "0");
                storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Log.i("Download Url",String.valueOf(downloadUrl));
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(userId);
                                HashMap<String, Object> image = new HashMap<>();
                                image.put("imagePath",String.valueOf(downloadUrl));
                                image.put("imageNumber","0");
                                databaseReference.push().setValue(image);
                            }
                        });

                        if(Integer.parseInt(gender)==2){
                            documentReference2.set(user);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Profile2Activity.this, QualitiesActivity.class);
                                    intent.putExtra("userId",userId);
                                    intent.putExtra("type","self");
                                    startActivity(intent);
                                }
                            });

                        }
                        else {
                            documentReference2.set(user);
                            documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Profile2Activity.this, QualitiesActivity.class);
                                    intent.putExtra("gender",gender);
                                    intent.putExtra("userId",userId);
                                    intent.putExtra("type","self");
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

            }
        });

    }

}