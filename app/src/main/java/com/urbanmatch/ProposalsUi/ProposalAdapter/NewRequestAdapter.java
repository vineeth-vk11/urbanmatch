package com.urbanmatch.ProposalsUi.ProposalAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.urbanmatch.ProposalsUi.ProposalDetailsFragment;
import com.urbanmatch.ProposalsUi.ProposalModels.NewRequestModel;
import com.urbanmatch.ProposalsUi.ProposalViewHolder.NewProposalViewHolder;
import com.urbanmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewRequestAdapter extends RecyclerView.Adapter<NewProposalViewHolder> {

    Context context;
    ArrayList<NewRequestModel> newRequestModelArrayList;
    Map<String,Object> userDetails = new HashMap<>();

    public NewRequestAdapter(Context context, ArrayList<NewRequestModel> newRequestModelArrayList, Map<String, Object> userDetails) {
        this.context = context;
        this.newRequestModelArrayList = newRequestModelArrayList;
        this.userDetails = userDetails;
    }

    @NonNull
    @Override
    public NewProposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_new_proposal,parent,false);
        return new NewProposalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewProposalViewHolder holder, final int position) {
        final List<SlideModel> slideModels = new ArrayList<>();

        holder.name.setText(newRequestModelArrayList.get(position).getName());
        holder.age.setText(newRequestModelArrayList.get(position).getAge());
        holder.height.setText(newRequestModelArrayList.get(position).getHeight());
        holder.education.setText(newRequestModelArrayList.get(position).getEducation());
        holder.occupation.setText(newRequestModelArrayList.get(position).getOccupation());
        holder.location.setText(String.format("%s,%s", newRequestModelArrayList.get(position).getCity(), newRequestModelArrayList.get(position).getCurrentState()));

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(newRequestModelArrayList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                    String imageTitle = "";
                    SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);
                    slideModels.add(slideModel);
                }
                holder.imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        final DocumentReference documentReference = db.collection("userData").document(uid).collection("ConnectionRequests").document(newRequestModelArrayList.get(position).getUid());
        final DocumentReference documentReference1 = db.collection("userData").document(newRequestModelArrayList.get(position).getUid()).collection("SentRequests").document(uid);
        final DocumentReference documentReference2 = db.collection("userData").document(uid).collection("Accepted").document(newRequestModelArrayList.get(position).getUid());
        final DocumentReference documentReference3 = db.collection("userData").document(newRequestModelArrayList.get(position).getUid()).collection("Accepted").document(uid);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> Accepted = new HashMap<>();
                Accepted.put("name",newRequestModelArrayList.get(position).getName());
                Accepted.put("age",newRequestModelArrayList.get(position).getAge());
                Accepted.put("city",newRequestModelArrayList.get(position).getCity());
                Accepted.put("currentState",newRequestModelArrayList.get(position).getCurrentState());
                Accepted.put("nativeState",newRequestModelArrayList.get(position).getNativeState());
                Accepted.put("education",newRequestModelArrayList.get(position).getEducation());
                Accepted.put("height",newRequestModelArrayList.get(position).getHeight());
                Accepted.put("uid",newRequestModelArrayList.get(position).getUid());
                Accepted.put("occupation",newRequestModelArrayList.get(position).getOccupation());
                Accepted.put("relationship",newRequestModelArrayList.get(position).getRelationship());
                Accepted.put("oppositeUserId",newRequestModelArrayList.get(position).getUid());

                documentReference.delete();
                documentReference1.delete();
                documentReference2.set(Accepted);
                documentReference3.set(userDetails);

            }
        });

        holder.proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProposalDetailsFragment proposalDetailsFragment = new ProposalDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("userId",newRequestModelArrayList.get(position).getUid());

                proposalDetailsFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, proposalDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return newRequestModelArrayList.size();
    }
}
