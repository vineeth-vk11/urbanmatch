package com.urbanmatch.profileUi;

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

import com.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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

    ArrayList<String> EDUCATION = new ArrayList<>();
    ArrayList<String> OCCUPATION = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        progressBar = findViewById(R.id.progressBar);

        Intent intent1 = getIntent();
        final String name = intent1.getStringExtra("name");
        final String lastName = intent1.getStringExtra("lastName");
        final String dob = intent1.getStringExtra("dob");
        final String gender = intent1.getStringExtra("gender");
        final String nativeState = intent1.getStringExtra("nativeState");
        final String currentState = intent1.getStringExtra("currentState");
        final String city = intent1.getStringExtra("city");
        final String age = intent1.getStringExtra("age");
        final Uri uri = Uri.parse(intent1.getStringExtra("imageUri"));

        final String fname = name;
        final String fdob = dob;
        final String fgender = gender;
        final String fnativeState = nativeState;
        final String fcurrentState = currentState;
        final String fcity = city;
        final String fage = age;
        final Uri furi = uri;

        EDUCATION.add("B.Arch");
        EDUCATION.add("B.Des");
        EDUCATION.add("B.E/B.TECH");
        EDUCATION.add("B.Pharma");
        EDUCATION.add("M.Arch");
        EDUCATION.add("M.Des");
        EDUCATION.add("M.E/M.TECH");
        EDUCATION.add("M.Pharma");
        EDUCATION.add("M.S(Engineering)");
        EDUCATION.add("B.IT");
        EDUCATION.add("BCA");
        EDUCATION.add("MCA/PGDCA");
        EDUCATION.add("B.Com");
        EDUCATION.add("CA");
        EDUCATION.add("CFA");
        EDUCATION.add("CS");
        EDUCATION.add("ICWA");
        EDUCATION.add("M.Com");
        EDUCATION.add("BBA");
        EDUCATION.add("BHM");
        EDUCATION.add("MBA/PGDM");
        EDUCATION.add("BAMS");
        EDUCATION.add("BDS");
        EDUCATION.add("BHMS");
        EDUCATION.add("BPT");
        EDUCATION.add("BVSc");
        EDUCATION.add("DM");
        EDUCATION.add("M.D");
        EDUCATION.add("M.S(Science)");
        EDUCATION.add("MBBS");
        EDUCATION.add("MCh");
        EDUCATION.add("MDS");
        EDUCATION.add("MPT");
        EDUCATION.add("MVSc");
        EDUCATION.add("BL/LLB");
        EDUCATION.add("ML/LLM");
        EDUCATION.add("B.A");
        EDUCATION.add("B.Ed");
        EDUCATION.add("B.Sc");
        EDUCATION.add("BFA");
        EDUCATION.add("BJMC");
        EDUCATION.add("M.A");
        EDUCATION.add("M.Ed");
        EDUCATION.add("M.Sc");
        EDUCATION.add("MFA");
        EDUCATION.add("MJMC");
        EDUCATION.add("MSW");
        EDUCATION.add("M.Phil");
        EDUCATION.add("Ph.D");
        EDUCATION.add("High School");
        EDUCATION.add("Trade School");
        EDUCATION.add("Diploma");
        EDUCATION.add("Others");

        OCCUPATION.add("Admin Professional");
        OCCUPATION.add("Clerk");
        OCCUPATION.add("Operator/Technician");
        OCCUPATION.add("Secretary/Front Office");
        OCCUPATION.add("Actor/Model");
        OCCUPATION.add("Advertising Professional");
        OCCUPATION.add("Film/Entertainment Professional");
        OCCUPATION.add("Journalist");
        OCCUPATION.add("Media Professional");
        OCCUPATION.add("PR Professional");
        OCCUPATION.add("Agriculture Professional");
        OCCUPATION.add("Farming");
        OCCUPATION.add("Airline Professional");
        OCCUPATION.add("Flight Attendant");
        OCCUPATION.add("Pilot");
        OCCUPATION.add("Architect");
        OCCUPATION.add("Accounting Professional");
        OCCUPATION.add("Auditor");
        OCCUPATION.add("Banking Professional");
        OCCUPATION.add("Chartered Professional");
        OCCUPATION.add("Finance Professional");
        OCCUPATION.add("BPO/ITes Professional");
        OCCUPATION.add("Customer Service");
        OCCUPATION.add("Analyst");
        OCCUPATION.add("Consultant");
        OCCUPATION.add("Corporate Communication");
        OCCUPATION.add("Corporate Planning");
        OCCUPATION.add("HR Professional");
        OCCUPATION.add("Marketing Professional");
        OCCUPATION.add("Operations Management");
        OCCUPATION.add("Product Manager");
        OCCUPATION.add("Program Manager");
        OCCUPATION.add("Project Manager - IT");
        OCCUPATION.add("Project Manager - Non IT");
        OCCUPATION.add("Sales Professional");
        OCCUPATION.add("Sr. Manager/Manager");
        OCCUPATION.add("Subject Matter Expert");
        OCCUPATION.add("Dentist");
        OCCUPATION.add("Doctor");
        OCCUPATION.add("Surgeon");
        OCCUPATION.add("Nurse");
        OCCUPATION.add("Paramedic");
        OCCUPATION.add("Pharmacist");
        OCCUPATION.add("Physiotherapist");
        OCCUPATION.add("Psycologist");
        OCCUPATION.add("Veterinary Doctor");
        OCCUPATION.add("Medical/Healthcare Professional");
        OCCUPATION.add("Education Professional");
        OCCUPATION.add("Educational Institutional Owner");
        OCCUPATION.add("Librarian");
        OCCUPATION.add("Professor/Lecturer");
        OCCUPATION.add("Research Assistant");
        OCCUPATION.add("Teacher");
        OCCUPATION.add("Electronics Engineer");
        OCCUPATION.add("Hardware/Telecom Engineer");
        OCCUPATION.add("Non-IT Engineer");
        OCCUPATION.add("Quality Assurance Engineer");
        OCCUPATION.add("Hotels/Hospitality Professional");
        OCCUPATION.add("Lawyer&Legal Professional");
        OCCUPATION.add("Mariner");
        OCCUPATION.add("Merchant Navy Officer");
        OCCUPATION.add("Research Professional");
        OCCUPATION.add("Science Professional");
        OCCUPATION.add("Scientist");
        OCCUPATION.add("Animator");
        OCCUPATION.add("Cyber/Network Security");
        OCCUPATION.add("Project Lead-IT");
        OCCUPATION.add("Quality Assurance Engineer-IT");
        OCCUPATION.add("Software Professional");
        OCCUPATION.add("UI/UX Designer");
        OCCUPATION.add("Web/Graphic Designer");
        OCCUPATION.add("Agent");
        OCCUPATION.add("Artist");
        OCCUPATION.add("Beautician");
        OCCUPATION.add("Broker");
        OCCUPATION.add("Fashion Designer");
        OCCUPATION.add("Fitness Professional");
        OCCUPATION.add("Interior Designer");
        OCCUPATION.add("Security Professional");
        OCCUPATION.add("Singer");
        OCCUPATION.add("Social services/NGO/Volunteer");
        OCCUPATION.add("Sportperson");
        OCCUPATION.add("Travel Professional");
        OCCUPATION.add("Writer");
        OCCUPATION.add("Other");
        OCCUPATION.add("CxO/Chairman/President/Director");
        OCCUPATION.add("VP/AVP/GM/DGM");


        String[] RELATION = new String[]{"Single", "Divorced", "Widower", "Annulled"};

        String[] HEIGHT = new String[]{"4'0", "4'1", "4'2", "4'3", "4'4", "4'5", "4'6", "4'7", "4'8", "4'9", "4'10", "4'11",
                "5'0", "5'1", "5'2", "5'3", "5'4", "5'5", "5'6", "5'7", "5'8", "5'9", "5'10", "5'11",
                "6'0", "6'1", "6'2", "6'3", "6'4", "6'5", "6'6", "6'7", "6'8", "6'9", "6'10", "6'11", "7'0"};

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
                else if(! OCCUPATION.contains(occupation)){
                    Toast.makeText(Profile2Activity.this,"Please select occupation from drop down ",Toast.LENGTH_SHORT).show();
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

                final DocumentReference documentReference = db.collection("femaleUserData").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                final DocumentReference documentReference1 = db.collection("maleUserData").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                final DocumentReference documentReference2 = db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

                final Map<String,Object> user = new HashMap<>();
                user.put("name",name);
                user.put("lastName",lastName);
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
                user.put("userId",FirebaseAuth.getInstance().getCurrentUser().getUid());
                user.put("instagram","");
                user.put("linkedin","");
                user.put("Aadhar","");
                user.put("subscription",false);
                user.put("subscribedOn","");
                user.put("profileStatus","1");

                final StorageReference storageReference1 = storageReference.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()) + "/" + UUID.randomUUID());
                storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Log.i("Download Url",String.valueOf(downloadUrl));
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                                    intent.putExtra("userId",FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                                    intent.putExtra("userId",FirebaseAuth.getInstance().getCurrentUser().getUid());
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