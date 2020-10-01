package com.urbanmatch.NewHomeHelper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewHomeAdapter extends PagerAdapter {

    Context context;
    ArrayList<UserModel> userModelArrayList;
    Map<String,Object> userDetails = new HashMap<>();
    LayoutInflater inflater;

    Boolean subscription;

    int connections;
    int photos;

    public NewHomeAdapter(Context context, ArrayList<UserModel> userModelArrayList, Map<String, Object> userDetails, Boolean subscription) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.userDetails = userDetails;
        this.subscription = subscription;
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

        final FirebaseDatabase firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String uid = firebaseUser.getUid();
        final String oppositeUserId = userModelArrayList.get(position).getUid();

        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Log.i("instagram",String.valueOf(userDetails.get("instagram")));

        final ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        final TextView nameAgeRelationship = view.findViewById(R.id.textView5);
        TextView height = view.findViewById(R.id.textView6);
        TextView profession = view.findViewById(R.id.textView7);
        TextView state = view.findViewById(R.id.textView8);
        final TextView city = view.findViewById(R.id.textView9);
        TextView score = view.findViewById(R.id.textView10);
        final Button connect = view.findViewById(R.id.connect);
        final TextView qualities = view.findViewById(R.id.qualities);
        final ImageButton instagramButton = view.findViewById(R.id.instagram);
        final ImageButton linkedinButton = view.findViewById(R.id.linkedin);

        if(!subscription){
            linkedinButton.setVisibility(View.INVISIBLE);
            instagramButton.setVisibility(View.INVISIBLE);
        }

        if(userModelArrayList.get(position).getLinkedin().equals("")){
            Log.i("hidden","true");
            linkedinButton.setVisibility(View.INVISIBLE);
        }

        if(userModelArrayList.get(position).getInstagram().equals("")){
            instagramButton.setVisibility(View.INVISIBLE);
        }

        linkedinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/"+userModelArrayList.get(position).getInstagram());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                intent.setPackage("com.instagram.android");

                try{
                    v.getContext().startActivity(intent);
                }catch (ActivityNotFoundException e){
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+userModelArrayList.get(position).getInstagram())));
                }
            }
        });

        linkedinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + userModelArrayList.get(position).getLinkedin()));
                final PackageManager packageManager = v.getContext().getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=" + userModelArrayList.get(position).getLinkedin()));
                }
                v.getContext().startActivity(intent);

            }
        });

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
            public void onDataChange(@NonNull final DataSnapshot snapshot1) {

                Log.i("method","entered photo");

                slideModels.clear();

                firebaseDatabase.getReference().child("userData").child("photos").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        photos = 0;

                        if(snapshot.exists()){
                            photos = Integer.parseInt(snapshot.child("photos").getValue().toString());
                        }
                        else {
                            photos = 0;
                        }

                        final HashMap<String, Object> photosNumber = new HashMap<>();
                        photosNumber.put("photos",userModelArrayList.size());

                        if(subscription){
                            Log.i("subscription","yes");

                            for(DataSnapshot dataSnapshot: snapshot1.getChildren()){
                                String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                                String imageTitle = "";
                                SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);
                                slideModels.add(slideModel);
                            }

                            imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
                            firebaseDatabase.getReference().child("userData").child("photos").child(date).setValue(photosNumber);

                        }

                        else {

                            if(photos>15){

                                Log.i("photos ","16");

                                for(DataSnapshot dataSnapshot: snapshot1.getChildren()){
                                    String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                                    String imageTitle = "";
                                    SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);

                                    if(slideModels.size() == 0){
                                        slideModels.add(slideModel);
                                        break;
                                    }
                                }
                                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
                                firebaseDatabase.getReference().child("userData").child("photos").child(date).setValue(photosNumber);

                            }
                            else {
                                Log.i("photos ","14");

                                for(DataSnapshot dataSnapshot: snapshot1.getChildren()){
                                    String imageUrl = dataSnapshot.child("imagePath").getValue().toString();
                                    String imageTitle = "";
                                    SlideModel slideModel = new SlideModel(imageUrl,imageTitle, ScaleTypes.CENTER_CROP);
                                    slideModels.add(slideModel);
                                }
                                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
                                firebaseDatabase.getReference().child("userData").child("photos").child(date).setValue(photosNumber);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
            public void onClick(final View v) {

                connections = 0;

                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

                final Map<String,Object> ConnectionSent = new HashMap<>();
                ConnectionSent.put("oppositeUserId",oppositeUserId);
                ConnectionSent.put("name",userModelArrayList.get(position).getName());
                ConnectionSent.put("age",userModelArrayList.get(position).getAge());
                ConnectionSent.put("relationship",userModelArrayList.get(position).getAge());
                ConnectionSent.put("profession",userModelArrayList.get(position).getOccupation());
                ConnectionSent.put("state",userModelArrayList.get(position).getNativeState());
                ConnectionSent.put("city",userModelArrayList.get(position).getCity());
                ConnectionSent.put("height",userModelArrayList.get(position).getHeight());
                ConnectionSent.put("education",userModelArrayList.get(position).getEducation());

                final Map<String, Object> AllConnections = new HashMap<>();
                AllConnections.put("userId",uid);
                AllConnections.put("OppositeUserId",oppositeUserId);

                final Map<String, Object> AllConnections2 = new HashMap<>();
                AllConnections2.put("userId",oppositeUserId);
                AllConnections2.put("OppositeUserId",uid);

                final FirebaseDatabase database;
                database = FirebaseDatabase.getInstance();

                database.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("connectionsSent").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            connections = Integer.parseInt(snapshot.child("connectionsSent").getValue().toString());
                        }
                        else {
                            connections = 0;
                        }

                        if(subscription){
                            if(connections>100){
                                Toast.makeText(v.getContext(),"You have reached the connections limit for today, upgrade to premium to connect",Toast.LENGTH_SHORT).show();
                            }
                            else {

                                HashMap<String, Object> connectionsSent = new HashMap<>();
                                connectionsSent.put("connectionsSent",connections+1);

                                database.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("connectionsSent").child(date).setValue(connectionsSent).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        db.collection("userData").document(uid).collection("AllConnections").document(oppositeUserId).set(AllConnections);
                                        db.collection("userData").document(oppositeUserId).collection("AllConnections").document(uid).set(AllConnections2);

                                        documentReference.set(ConnectionSent);
                                        documentReference1.set(userDetails);

                                        connect.setClickable(false);
                                        connect.setText("sent");
                                    }
                                });
                            }
                        }
                        else {
                            if(connections>15){
                                Toast.makeText(v.getContext(),"You have reached the connections limit for today, upgrade to premium to connect",Toast.LENGTH_SHORT).show();
                            }
                            else {

                                HashMap<String, Object> connectionsSent = new HashMap<>();
                                connectionsSent.put("connectionsSent",connections+1);

                                database.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("connectionsSent").child(date).setValue(connectionsSent).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        db.collection("userData").document(uid).collection("AllConnections").document(oppositeUserId).set(AllConnections);
                                        db.collection("userData").document(oppositeUserId).collection("AllConnections").document(uid).set(AllConnections2);

                                        documentReference.set(ConnectionSent);
                                        documentReference1.set(userDetails);

                                        connect.setClickable(false);
                                        connect.setText("sent");
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        container.addView(view);
        return view;
    }
}
