package com.urbanmatch.MoreUi;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PartnerFragment extends Fragment {

    AutoCompleteTextView minAge;
    AutoCompleteTextView maxAge;
    EditText occupations;
    EditText relationshipStatus;
    AutoCompleteTextView minHeight;
    AutoCompleteTextView maxHeight;

    boolean[] checkedOccupations;
    ArrayList<Integer> userOccupationSelections = new ArrayList<>();

    boolean[] checkedRelations;
    ArrayList<Integer> userRelationSelections = new ArrayList<>();

    ArrayAdapter<String> adapter ;

    ArrayList<String> MinAges;
    ArrayList<String> MaxAges;
    ArrayList<String> MinHeights;
    ArrayList<String> MaxHeights;

    RadioGroup gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partner, container, false);

        minAge = view.findViewById(R.id.age_min_dropdown);
        maxAge = view.findViewById(R.id.age_max_dropdown);
        minHeight = view.findViewById(R.id.height_min_dropdown);
        maxHeight = view.findViewById(R.id.height_max_dropdown);
        gender = view.findViewById(R.id.radioGroup);

        MinAges = new ArrayList<>();
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

        MaxAges = new ArrayList<>();

        MinHeights = new ArrayList<>();
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

        MaxHeights = new ArrayList<>();

        final String[] RELATION = new String[]{"Single", "Awaiting Divorce", "Divorced", "Widower", "Annulled"};

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

        adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                MinAges
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                MaxAges
        );

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                OCCUPATION
        );

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                RELATION
        );

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                MinHeights
        );

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                MaxHeights
        );

        minAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MaxAges.clear();
                int ageSlected = Integer.parseInt(minAge.getText().toString());

                for(int i = position+1; i<MinAges.size() ; i++){
                    MaxAges.add(MinAges.get(i).toString());
                }
            }
        });

        minHeight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MaxHeights.clear();
                for(int i = position+1; i<MinHeights.size(); i++){
                    MaxHeights.add(MinHeights.get(i).toString());
                }
            }
        });

        minAge.setAdapter(adapter);
        maxAge.setAdapter(adapter2);
        minHeight.setAdapter(adapter5);
        maxHeight.setAdapter(adapter6);

        occupations = view.findViewById(R.id.occupationSelect);
        checkedOccupations = new boolean[OCCUPATION.length];

        occupations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

        relationshipStatus = view.findViewById(R.id.relationSelect);
        checkedRelations = new boolean[RELATION.length];

        relationshipStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


        getData();

        return view;
    }
    private void getData(){
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("userRequirementData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String minAgeF = documentSnapshot.getString("minAge");
                minAge.setText(documentSnapshot.getString("minAge"));
                maxAge.setText(documentSnapshot.getString("maxAge"));
                minHeight.setText(documentSnapshot.getString("minHeight"));
                maxHeight.setText(documentSnapshot.getString("maxHeight"));

                userOccupationSelections = (ArrayList<Integer>)documentSnapshot.get("occupation");
                userRelationSelections = (ArrayList<Integer>) documentSnapshot.get("relationship");

                Log.i("occupations", String.valueOf(userOccupationSelections.size()));
                Log.i("relations",String.valueOf(userRelationSelections));
            }
        });
    }
}