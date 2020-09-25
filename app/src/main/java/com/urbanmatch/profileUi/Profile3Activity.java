package com.urbanmatch.profileUi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile3Activity extends AppCompatActivity {

    Button  next;
    FloatingActionButton addQualities;

    FirebaseFirestore db;

    boolean[] checkedOccupations;
    ArrayList<Integer> userOccupationSelections = new ArrayList<>();

    boolean[] checkedRelations;
    ArrayList<Integer> userRelationSelections = new ArrayList<>();

    String[] MAXAGE = new String[]{};

    EditText occupations;
    EditText relationshipStatus;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        final ArrayList<String> MinAges = new ArrayList<>();
        MinAges.add("24");
        MinAges.add("25");
        MinAges.add("26");
        MinAges.add("27");
        MinAges.add("28");
        MinAges.add("29");
        MinAges.add("30");
        MinAges.add("31");
        MinAges.add("32");
        MinAges.add("33");
        MinAges.add("34");
        MinAges.add("35");
        MinAges.add("36");
        MinAges.add("37");
        MinAges.add("38");
        MinAges.add("39");
        MinAges.add("40");

        final ArrayList<String> MaxAges = new ArrayList<>();

        final  ArrayList<String> MinHeights = new ArrayList<>();
        MinHeights.add("4'0");
        MinHeights.add("4'1");
        MinHeights.add("4'2");
        MinHeights.add("4'3");
        MinHeights.add("4'4");
        MinHeights.add("4'5");
        MinHeights.add("4'6");
        MinHeights.add("4'7");
        MinHeights.add("4'8");
        MinHeights.add("4'9");
        MinHeights.add("4'10");
        MinHeights.add("4'11");
        MinHeights.add("5'1");
        MinHeights.add("5'2");
        MinHeights.add("5'3");
        MinHeights.add("5'4");
        MinHeights.add("5'5");
        MinHeights.add("5'6");
        MinHeights.add("5'7");
        MinHeights.add("5'8");
        MinHeights.add("5'9");
        MinHeights.add("5'10");
        MinHeights.add("5'11");
        MinHeights.add("6'0");
        MinHeights.add("6'1");
        MinHeights.add("6'2");
        MinHeights.add("6'3");
        MinHeights.add("6'4");
        MinHeights.add("6'5");
        MinHeights.add("6'6");
        MinHeights.add("6'7");
        MinHeights.add("6'8");
        MinHeights.add("6'9");
        MinHeights.add("6'10");
        MinHeights.add("6'11");
        MinHeights.add("7'00");

        final ArrayList<String> MaxHeights = new ArrayList<>();

        final String[] RELATION = new String[]{"Single", "Divorced", "Widower", "Annulled"};

        final String[] OCCUPATION = new String[]{"Admin Professional", "Clerk", "Operator/Technician", "Secretary/Front Office", "Actor/Model",
                "Advertising Professional", "Film/Entertainment Professional", "Journalist", "Media Professional", "PR Professional",
                "Agriculture Professional", "Farming", "Airline Professional", "Flight Attendant", "Pilot", "Architect", "Accounting Professional",
                "Auditor", "Banking Professional", "Chartered Professional", "Finance Professional", "Finance Professional", "BPO/ITes Professional",
                "Customer Service", "Analyst", "Consultant", "Corporate Communication", "Corporate Planning", "HR Professional", "Marketing Professional",
                "Operations Management", "Product Manager", "Program Manager", "Project Manager - IT", "Project Manager - Non IT", "Sales Professional",
                "Sr. Manager/Manager", "Subject Matter Expert", "Dentist", "Doctor", "Surgeon", "Nurse", "Paramedic", "Pharmacist", "Physiotherapist", "Psycologist",
                "Veterinary Doctor", "Medical/Healthcare Professional", "Education Professional", "Educational Institutional Owner", "Librarian", "Professor/Lecturer",
                "Research Assistant", "Teacher", "Electronics Engineer", "Hardware/Telecom Engineer", "Non-IT Engineer", "Quality Assurance Engineer",
                "Hotels/Hospitality Professional", "Lawyer&Legal Professional", "Mariner", "Merchant Navy Officer", "Research Professional", "Science Professional",
                "Scientist", "Animator", "Cyber/Network Security", "Project Lead-IT", "Quality Assurance Engineer-IT", "Software Professional", "UI/UX Designer",
                "Web/Graphic Designer", "Agent", "Artist", "Beautician", "Broker", "Fashion Designer", "Fitness Professional", "Interior Designer", "Security Professional",
                "Singer", "Social services/NGO/Volunteer", "Sportperson", "Travel Professional", "Writer", "Other", "CxO/Chairman/President/Director",
                "VP/AVP/GM/DGM"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MinAges
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MaxAges
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
                MinHeights
        );

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                MaxHeights
        );

        final AutoCompleteTextView minAges = findViewById(R.id.age_min_dropdown);
        final AutoCompleteTextView maxAges = findViewById(R.id.age_max_dropdown);
        final AutoCompleteTextView minHeights = findViewById(R.id.height_min_dropdown);
        final AutoCompleteTextView maxHeights = findViewById(R.id.height_max_dropdown);

        minAges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MaxAges.clear();
                int ageSelected = Integer.parseInt(minAges.getText().toString());

                for(int i = position+1; i<MinAges.size() ; i++){
                    MaxAges.add(MinAges.get(i).toString());
                }
                Log.i("position",String.valueOf(position));
                Log.i("id",String.valueOf(id));
                Log.i("selected age",String.valueOf(ageSelected));
            }
        });

        minHeights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MaxHeights.clear();

                for(int i = position+1; i<MinHeights.size(); i++){
                    MaxHeights.add(MinHeights.get(i).toString());
                }
            }
        });

        minAges.setAdapter(adapter);
        maxAges.setAdapter(adapter2);

        minHeights.setAdapter(adapter5);
        maxHeights.setAdapter(adapter6);

        occupations = findViewById(R.id.occupationSelect);
        checkedOccupations = new boolean[OCCUPATION.length];

        radioGroup = findViewById(R.id.radioGroup);

        occupations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile3Activity.this);
                builder.setTitle("Occupations");
                builder.setMultiChoiceItems(OCCUPATION, checkedOccupations, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! userOccupationSelections.contains(position)){
                                userOccupationSelections.add(position);
                            }
                        }
                        else if(userOccupationSelections.contains(position)){
                            userOccupationSelections.remove(userOccupationSelections.indexOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selections = "";
                        for(int i =0; i<userOccupationSelections.size();i++){
                            selections = selections + OCCUPATION[userOccupationSelections.get(i)];
                            if( i != userOccupationSelections.size() - 1){
                                selections = selections + ", ";
                            }
                        }
                        occupations.setText(selections);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<userOccupationSelections.size();i++){
                            checkedOccupations[i] = false;
                            userOccupationSelections.clear();
                            occupations.setText("");

                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        relationshipStatus = findViewById(R.id.relationSelect);
        checkedRelations = new boolean[RELATION.length];

        relationshipStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile3Activity.this);
                builder.setTitle("Relations");
                builder.setMultiChoiceItems(RELATION, checkedRelations, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! userRelationSelections.contains(position)){
                                userRelationSelections.add(position);
                            }
                        }
                        else if(userRelationSelections.contains(position)){
                            userRelationSelections.remove(userRelationSelections.indexOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String relationSelections = "";
                        for(int i =0; i<userRelationSelections.size();i++){
                            relationSelections = relationSelections + RELATION[userRelationSelections.get(i)];
                            if( i != userRelationSelections.size() - 1){
                                relationSelections = relationSelections + ", ";
                            }
                        }
                        relationshipStatus.setText(relationSelections);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<userRelationSelections.size();i++){
                            checkedRelations[i] = false;
                            userRelationSelections.clear();
                            relationshipStatus.setText("");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        next = findViewById(R.id.next2);
        db = FirebaseFirestore.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String minAge = minAges.getText().toString().trim();
                final String maxAge = maxAges.getText().toString().trim();
                final ArrayList<Integer> occupation = userOccupationSelections;
                final ArrayList<Integer> relationship = userRelationSelections;
                final String minHeight = minHeights.getText().toString().trim();
                final String maxHeight = maxHeights.getText().toString().trim();
                int fromSameState = radioGroup.getCheckedRadioButtonId();

                if(TextUtils.isEmpty(minAge)){
                    Toast.makeText(Profile3Activity.this,"Please select Minimum Age",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(maxAge)){
                    Toast.makeText(Profile3Activity.this,"Please select Maximum Age",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(occupation.size()<1){
                    Toast.makeText(Profile3Activity.this,"Please select Occupation",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(relationship.size()<1){
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
                else if(Integer.parseInt(maxAge)<=Integer.parseInt(minAge)){
                    Toast.makeText(Profile3Activity.this,"Maximum height is less than minimum height",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(String.valueOf(fromSameState).isEmpty()){
                    Toast.makeText(Profile3Activity.this,"Please select an option for partner from same state",Toast.LENGTH_SHORT).show();
                    return;
                }

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("userRequirementData").document(uid);
                final DocumentReference documentReference1 = db.collection("userData").document(uid);

                final Map<String, Object> status = new HashMap<>();
                status.put("profileStatus","3");

                Map<String,Object> user = new HashMap<>();
                user.put("minAge",minAge);
                user.put("maxAge",maxAge);
                user.put("occupation",occupation);
                user.put("relationship",relationship);
                user.put("minHeight",minHeight);
                user.put("maxHeight",maxHeight);
                user.put("fromSameState",fromSameState);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        documentReference1.update(status).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(Profile3Activity.this, QualitiesActivity.class);
                                intent.putExtra("type","required");
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        });
    }
}