package com.urbanmatch.NewHomeHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.urbanmatch.HomeHelper.UserModel;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewHomeAdapter extends PagerAdapter {

    Context context;
    ArrayList<UserModel> userModelArrayList;
    Map<String,Object> userDetails = new HashMap<>();
    LayoutInflater inflater;

    public NewHomeAdapter(Context context, ArrayList<UserModel> userModelArrayList, Map<String, Object> userDetails) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.userDetails = userDetails;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.list_item_home, container, false);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String uid = firebaseUser.getUid();
        final String oppositeUserId = userModelArrayList.get(position).getUid();


        final ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        final TextView nameAgeRelationship = view.findViewById(R.id.textView5);
        TextView height = view.findViewById(R.id.textView6);
        TextView profession = view.findViewById(R.id.textView7);
        TextView state = view.findViewById(R.id.textView8);
        TextView city = view.findViewById(R.id.textView9);
        TextView score = view.findViewById(R.id.textView10);
        final Button connect = view.findViewById(R.id.connect);
        final TextView qualities = view.findViewById(R.id.qualities);

        nameAgeRelationship.setText(String.format("%s %s, %s, %s", userModelArrayList.get(position).getName(), userModelArrayList.get(position).getLastName(), userModelArrayList.get(position).getAge(), userModelArrayList.get(position).getRelationship()));
        profession.setText(userModelArrayList.get(position).getOccupation());
        state.setText("From "+userModelArrayList.get(position).getNativeState());
        city.setText(String.format("Lives in %s", userModelArrayList.get(position).getCity()));
        height.setText(userModelArrayList.get(position).getHeight());
        score.setText(String.valueOf(userModelArrayList.get(position).getfScore()));

        final List<SlideModel> slideModels = new ArrayList<>();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(userModelArrayList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slideModels.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                    String imageTitle = "";
                    SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);
                    slideModels.add(slideModel);
                }
                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final StringBuffer stringBuffer = new StringBuffer();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").child(userModelArrayList.get(position).getUid())
                .child("self");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    stringBuffer.append("#"+dataSnapshot.child("quality").getValue(true).toString()+" ");
                }
                qualities.setText(stringBuffer.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseFirestore db1;
        db1 = FirebaseFirestore.getInstance();
        db1.collection("userData").document(uid).collection("AllConnections").whereEqualTo("OppositeUserId",oppositeUserId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    if(queryDocumentSnapshots.size() == 0){
                        connect.setVisibility(View.VISIBLE);
                    }
                    else {
                        connect.setVisibility(View.INVISIBLE);
                    }
                }
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
                        connect.setClickable(false);
                    }
                }
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

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

                Map<String, Object> AllConnections = new HashMap<>();
                AllConnections.put("userId",uid);
                AllConnections.put("OppositeUserId",oppositeUserId);

                Map<String, Object> AllConnections2 = new HashMap<>();
                AllConnections2.put("userId",oppositeUserId);
                AllConnections2.put("OppositeUserId",uid);

                db.collection("userData").document(uid).collection("AllConnections").document(oppositeUserId).set(AllConnections);
                db.collection("userData").document(oppositeUserId).collection("AllConnections").document(uid).set(AllConnections2);

                documentReference.set(ConnectionSent);
                documentReference1.set(userDetails);

                connect.setClickable(false);
                connect.setText("sent");

            }
        });


        container.addView(view);
        return view;
    }
}
