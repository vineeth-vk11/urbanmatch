package com.urbanmatch.HomeUi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.urbanmatch.LoginHelper.LoginMobileNumberActivity;
import com.urbanmatch.MoreUi.ContactUsFragment;
import com.urbanmatch.MoreUi.MembershipActivity;
import com.urbanmatch.MoreUi.PartnerFragment;
import com.urbanmatch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoreFragment extends Fragment {

    Button partner, membership, logout;
    Button deleteAccount;
    Button contactUs;

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        partner = view.findViewById(R.id.preferred_partner);
        membership = view.findViewById(R.id.membership);
        logout = view.findViewById(R.id.logout);
        deleteAccount = view.findViewById(R.id.deleteAccount);
        contactUs = view.findViewById(R.id.contactUs);
        progressBar = view.findViewById(R.id.progressBar6);

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,contactUsFragment );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartnerFragment partnerFragment = new PartnerFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,partnerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MembershipActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(getContext(), LoginMobileNumberActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Account")
                        .setMessage("Do you really want to delete your account?")
                        .setIcon(R.drawable.ic_baseline_delete_forever_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressBar.setVisibility(View.VISIBLE);
                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        final String gender = documentSnapshot.getString("gender");

                                        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                String type = "";
                                                if(gender.equals("2")){
                                                    type = "femaleUserData";
                                                }
                                                else if(gender.equals("1")){
                                                    type = "maleUserData";
                                                }
                                                db.collection(type).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        db.collection("userRequirementData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                final FirebaseDatabase db;
                                                                db = FirebaseDatabase.getInstance();
                                                                db.getReference().child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        db.getReference().child("UserQualitiesData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                db.getReference().child("photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
                                                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                                                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                FirebaseAuth.getInstance().signOut();
                                                                                                Intent intent = new Intent(getContext(), LoginMobileNumberActivity.class);
                                                                                                startActivity(intent);
                                                                                                getActivity().finish();
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                            }
                        }).setNegativeButton("No",null).show();
            }
        });
        return view;
    }
}