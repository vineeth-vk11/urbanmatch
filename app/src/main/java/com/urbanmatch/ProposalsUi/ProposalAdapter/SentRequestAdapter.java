package com.urbanmatch.ProposalsUi.ProposalAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.urbanmatch.ProposalsUi.ProposalDetailsFragment;
import com.urbanmatch.ProposalsUi.ProposalModels.SentRequestModel;
import com.urbanmatch.ProposalsUi.ProposalViewHolder.SentRequestViewHolder;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestViewHolder> {
    Context context;
    ArrayList<SentRequestModel> sentRequestModelArrayList;

    public SentRequestAdapter(Context context, ArrayList<SentRequestModel> sentRequestModelArrayList) {
        this.context = context;
        this.sentRequestModelArrayList = sentRequestModelArrayList;
    }

    @NonNull
    @Override
    public SentRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_sent,parent,false);
        return new SentRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SentRequestViewHolder holder, final int position) {
        final List<SlideModel> slideModels = new ArrayList<>();

        final String oppositeUserId = sentRequestModelArrayList.get(position).getOppositeUserId();
        holder.sentName.setText(sentRequestModelArrayList.get(position).getName());
        holder.sentAge.setText(sentRequestModelArrayList.get(position).getAge());
        holder.sentHeight.setText(sentRequestModelArrayList.get(position).getHeight());
        holder.sentEducation.setText(sentRequestModelArrayList.get(position).getEducation());
        holder.sentOccupation.setText(sentRequestModelArrayList.get(position).getProfession());
        holder.sentLocation.setText(String.format("%s,%s", sentRequestModelArrayList.get(position).getCity(), sentRequestModelArrayList.get(position).getState()));

        Log.i("oUid",oppositeUserId);

        if(oppositeUserId != null){
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(oppositeUserId);
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
        }
        final FirebaseFirestore db;
        final FirebaseFirestore db1;

        db = FirebaseFirestore.getInstance();
        db1 = FirebaseFirestore.getInstance();

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db1.collection("userData").document(oppositeUserId).collection("ConnectionRequests").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("SentRequests").document(oppositeUserId)
                                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("AllConnections").document(oppositeUserId)
                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context,"Request deleted",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        holder.connectedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProposalDetailsFragment proposalDetailsFragment = new ProposalDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("userId",sentRequestModelArrayList.get(position).getOppositeUserId());

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
        return sentRequestModelArrayList.size();
    }
}
