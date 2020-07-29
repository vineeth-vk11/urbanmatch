package com.example.urbanmatch.UpdateProfileUi;

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

import com.example.urbanmatch.HomeUi.ProfileFragment;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class UpdateDetailsFragment extends Fragment {

    String occupation, education, linkedin, instagram;

    EditText txtlinkedin, txtinstagram;

    Button save;

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

        String[] EDUCATION = new String[]{"B.Arch", "B.Des", "B.E/B.TECH", "B.Pharma", "M.Arch", "M.Des", "M.E/M.TECH", "M.Pharma", "M.S(Engineering)"};
        String[] OCCUPATION = new String[]{"Occupation1", "Occupation2"};

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