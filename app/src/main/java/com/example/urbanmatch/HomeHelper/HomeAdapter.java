package com.example.urbanmatch.HomeHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    Context context;
    ArrayList<UserModel> userModelArrayList;
    Map<String,Object> userDetails = new HashMap<>();

    public HomeAdapter(Context context, ArrayList<UserModel> userModelArrayList) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }

    public HomeAdapter(Context context, ArrayList<UserModel> userModelArrayList, Map<String, Object> userDetails) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.userDetails = userDetails;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_home,parent,false);
        return new HomeViewHolder(view);
   }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {

        final List<SlideModel> slideModels = new ArrayList<>();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String uid = firebaseUser.getUid();
        final String oppositeUserId = userModelArrayList.get(position).getUid();

        holder.nameAgeRelationship.setText(String.format("%s,%s,%s", userModelArrayList.get(position).getName(), userModelArrayList.get(position).getAge(), userModelArrayList.get(position).getRelationship()));
        holder.profession.setText(userModelArrayList.get(position).getOccupation());
        holder.state.setText(userModelArrayList.get(position).getNativeState());
        holder.city.setText(userModelArrayList.get(position).getCity());
        holder.height.setText(userModelArrayList.get(position).getHeight());
        holder.score.setText(String.valueOf(userModelArrayList.get(position).getfScore()));

        final StringBuffer stringBuffer = new StringBuffer();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").child(userModelArrayList.get(position).getUid())
                .child("self");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    stringBuffer.append("#"+dataSnapshot.child("quality").getValue(true).toString()+" ");
                }
                holder.qualities.setText(stringBuffer.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(userModelArrayList.get(position).getUid());
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
        final DocumentReference documentReference = db.collection("userData").document(uid).collection("SentRequests").document(oppositeUserId);
        final DocumentReference documentReference1 = db.collection("userData").document(oppositeUserId).collection("ConnectionRequests").document(uid);

        Query query = db.collection("userData").document(uid).collection("sentRequests").whereEqualTo("oppositeUserId",oppositeUserId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().size() != 0){
                            holder.connect.setClickable(false);
                    }
                }
            }
        });

        holder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> ConnectionSent = new HashMap<>();
                ConnectionSent.put("oppositeUserId",oppositeUserId);
                ConnectionSent.put("name",userModelArrayList.get(position).getName());
                ConnectionSent.put("age",userModelArrayList.get(position).getAge());
                ConnectionSent.put("relationship",userModelArrayList.get(position).getAge());
                ConnectionSent.put("profession",userModelArrayList.get(position).getOccupation());
                ConnectionSent.put("state",userModelArrayList.get(position).getNativeState());
                ConnectionSent.put("city",userModelArrayList.get(position).getCity());
                ConnectionSent.put("height",userModelArrayList.get(position).getHeight());
                ConnectionSent.put("education",userModelArrayList.get(position).getEducation());

                documentReference.set(ConnectionSent);
                documentReference1.set(userDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public ArrayList<UserModel> getUserModelArrayList() {
        return userModelArrayList;
    }

    public void setUserModelArrayList(ArrayList<UserModel> userModelArrayList) {
        this.userModelArrayList = userModelArrayList;
    }
}
