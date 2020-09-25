package com.urbanmatch.UpdateProfileUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.urbanmatch.HomeUi.ProfileFragment;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class UpdateDetailsFragment extends Fragment {

    String occupation, education, linkedin, instagram;

    EditText txtlinkedin, txtinstagram;

    Button save;

    ArrayList<String> EDUCATION = new ArrayList<>();
    ArrayList<String> OCCUPATION = new ArrayList<>();


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);

        Bundle bundle = getArguments();
        occupation = bundle.getString("occupation");
        education = bundle.getString("education");
        linkedin = bundle.getString("linkedin");
        instagram = bundle.getString("instagram");


        txtlinkedin = view.findViewById(R.id.linkedin_edit);
        txtinstagram = view.findViewById(R.id.instagram_edit);

        txtinstagram.setText(instagram);
        txtlinkedin.setText(linkedin);


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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_menu_popup_item,
                EDUCATION
        );

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_menu_popup_item,
                OCCUPATION
        );

        final AutoCompleteTextView educations = view.findViewById(R.id.profession_edit);
        final AutoCompleteTextView occupations = view.findViewById(R.id.education_edit);

        educations.setAdapter(adapter);
        occupations.setAdapter(adapter1);

        educations.setText(education);
        occupations.setText(occupation);

        save = view.findViewById(R.id.save_details);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String occupation = occupations.getText().toString();
                String education = educations.getText().toString();
                String instagram = txtinstagram.getText().toString();
                String linkedin = txtlinkedin.getText().toString();

                if(occupation.isEmpty()){
                    Toast.makeText(getActivity(),"Enter select a occupation",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(education.isEmpty()){
                    Toast.makeText(getActivity(),"Enter select a education",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!EDUCATION.contains(education)){
                    Toast.makeText(getActivity(),"Select education from drop down",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(! OCCUPATION.contains(occupation)){
                    Toast.makeText(getActivity(),"Select occupation from dropdown",Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> updatedData = new HashMap<>();
                updatedData.put("occupation",occupation);
                updatedData.put("education",education);
                updatedData.put("instagram",instagram);
                updatedData.put("linkedin",linkedin);

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                documentReference.update(updatedData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_frame,profileFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

            }
        });

        return view;
    }
}