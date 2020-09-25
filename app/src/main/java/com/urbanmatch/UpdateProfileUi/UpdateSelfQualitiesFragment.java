package com.urbanmatch.UpdateProfileUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.urbanmatch.HomeUi.ProfileFragment;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateSelfQualitiesFragment extends Fragment {

    private Chip adventorous, ambitious, ambivert, artist, calm, caring,
            chailover, charming, cheerful, chocaholic, coffelover, creative, curious,
            dancer, doglover, dreamer, emotional, energetic, entertainer, entrepreneur,
            extrovert, faithful, fitnessfreak, foodie, friendly, funny, gadgetlover, gameaddict,
            geek, gym, humorous, independent, intellectual, introvert, kind, loyal, meditation, moody, motivated,
            moviebuff, musiclover, mysterious, naturelover, nightowl, openminded, optimistic, partyhopper, reader, religious,
            romantic, sarcastic, sexy, shopaholic, shy, simple, spiritual, sportsenthusiast, stylish, talkative, techie, traveller, workaholic, writer, yoga;

    ChipGroup chipGroup;

    private ArrayList<String> selectedQualities;

    private ArrayList<String> fetchedQualities;

    private ArrayList<Chip> chipArrayList;

    Button save;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_self_qualities, container, false);

        selectedQualities = new ArrayList<>();
        chipGroup = view.findViewById(R.id.qualities_group);

        adventorous = view.findViewById(R.id.adventurous);
        ambitious = view.findViewById(R.id.ambitious);
        ambivert = view.findViewById(R.id.ambivert);
        artist = view.findViewById(R.id.artist);
        calm = view.findViewById(R.id.calm);
        caring = view.findViewById(R.id.caring);
        chailover = view.findViewById(R.id.chailover);
        charming = view.findViewById(R.id.charming);
        cheerful = view.findViewById(R.id.cheerful);
        chocaholic = view.findViewById(R.id.chocaholic);
        coffelover = view.findViewById(R.id.coffelover);
        creative = view.findViewById(R.id.creative);
        curious = view.findViewById(R.id.curious);
        dancer = view.findViewById(R.id.dance);
        doglover = view.findViewById(R.id.doglover);
        dreamer = view.findViewById(R.id.dreamer);
        emotional = view.findViewById(R.id.emotional);
        energetic = view.findViewById(R.id.energetic);
        entertainer = view.findViewById(R.id.entertainer);
        entrepreneur = view.findViewById(R.id.entertainer);
        extrovert = view.findViewById(R.id.extrovert);
        faithful = view.findViewById(R.id.faithful);
        fitnessfreak = view.findViewById(R.id.fitnessfreak);
        foodie = view.findViewById(R.id.foodie);
        friendly = view.findViewById(R.id.friendly);
        funny = view.findViewById(R.id.funny);
        gadgetlover = view.findViewById(R.id.gadgetlover);
        gameaddict = view.findViewById(R.id.gameaddict);
        geek = view.findViewById(R.id.geek);
        gym = view.findViewById(R.id.gym);
        humorous = view.findViewById(R.id.humorous);
        independent = view.findViewById(R.id.independent);
        intellectual = view.findViewById(R.id.intellectual);
        introvert = view.findViewById(R.id.introvert);
        kind = view.findViewById(R.id.kind);
        loyal = view.findViewById(R.id.loyal);
        meditation = view.findViewById(R.id.meditation);
        moody = view.findViewById(R.id.moody);
        motivated = view.findViewById(R.id.motivated);
        moviebuff = view.findViewById(R.id.moviebuff);
        musiclover = view.findViewById(R.id.musiclover);
        mysterious = view.findViewById(R.id.mysterious);
        naturelover = view.findViewById(R.id.naturelover);
        nightowl = view.findViewById(R.id.nightowl);
        openminded = view.findViewById(R.id.openminded);
        optimistic = view.findViewById(R.id.optimistic);
        partyhopper = view.findViewById(R.id.partyhopper);
        reader = view.findViewById(R.id.reader);
        religious = view.findViewById(R.id.religious);
        romantic = view.findViewById(R.id.romantic);
        sarcastic = view.findViewById(R.id.sarcastic);
        sexy = view.findViewById(R.id.sexy);
        shopaholic = view.findViewById(R.id.shopaholic);
        shy = view.findViewById(R.id.shy);
        simple = view.findViewById(R.id.simple);
        spiritual = view.findViewById(R.id.spiritual);
        sportsenthusiast = view.findViewById(R.id.sportsenthusiast);
        stylish = view.findViewById(R.id.stylish);
        talkative = view.findViewById(R.id.talkative);
        techie = view.findViewById(R.id.techie);
        traveller = view.findViewById(R.id.traveller);
        workaholic = view.findViewById(R.id.workaholic);
        writer = view.findViewById(R.id.writer);
        yoga = view.findViewById(R.id.yoga);



        if(selectedQualities.size()>=10){
            chipGroup.setClickable(false);
        }

        fetchedQualities = new ArrayList<>();
        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();
        db.getReference("UserQualitiesData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("self").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String quality = dataSnapshot.child("quality").getValue().toString();
                    fetchedQualities.add(quality);

                    if(quality.equals("Adventurous")){
                        adventorous.setChecked(true);
                    }
                    else if(quality.equals("Ambitious")){
                        ambitious.setChecked(true);
                    }
                    else if (quality.equals("Ambivert")){
                        ambitious.setChecked(true);
                    }
                    else if (quality.equals("Artist")){
                        artist.setChecked(true);
                    }
                    else if (quality.equals("Calm")){

                        calm.setChecked(true);
                    }
                    else if (quality.equals("Caring")){
                        caring.setChecked(true);
                    }
                    else if (quality.equals("ChaiLover")){
                        chailover.setChecked(true);
                    }
                    else if (quality.equals("Charming")){
                        charming.setChecked(true);
                    }
                    else if (quality.equals("Cheerful")){
                        cheerful.setChecked(true);
                    }
                    else if (quality.equals("Chocaholic")){
                        chocaholic.setChecked(true);
                    }
                    else if (quality.equals("CoffeLover")){
                        coffelover.setChecked(true);
                    }
                    else if (quality.equals("Creative")){
                        creative.setChecked(true);
                    }
                    else if (quality.equals("Curious")){
                        curious.setChecked(true);
                    }
                    else if (quality.equals("Dancer")){
                        dancer.setChecked(true);
                    }
                    else if (quality.equals("DogLover")){
                        doglover.setChecked(true);
                    }
                    else if (quality.equals("Dreamer")){
                        dreamer.setChecked(true);
                    }
                    else if (quality.equals("Emotional")){
                        emotional.setChecked(true);
                    }
                    else if (quality.equals("Energetic")){
                        energetic.setChecked(true);
                    }
                    else if (quality.equals("Entertainer")){
                        entertainer.setChecked(true);
                    }
                    else if (quality.equals("Entrepreneur")){
                        entrepreneur.setChecked(true);
                    }
                    else if (quality.equals("Extrovert")){
                        extrovert.setChecked(true);
                    }
                    else if (quality.equals("Faithful")){
                        faithful.setChecked(true);
                    }
                    else if (quality.equals("FitnessFreak")){
                        fitnessfreak.setChecked(true);
                    }
                    else if (quality.equals("Foodie")){
                        foodie.setChecked(true);
                    }
                    else if (quality.equals("Friendly")){
                        friendly.setChecked(true);
                    }
                    else if (quality.equals("Funny")){
                        funny.setChecked(true);
                    }
                    else if (quality.equals("GadgetLover")){
                        gadgetlover.setChecked(true);
                    }
                    else if (quality.equals("GameAddict")){
                        gameaddict.setChecked(true);
                    }
                    else if (quality.equals("Geek")){
                        geek.setChecked(true);
                    }
                    else if (quality.equals("Gym")){
                        gym.setChecked(true);
                    }
                    else if (quality.equals("Humorous")){
                        humorous.setChecked(true);
                    }
                    else if (quality.equals("Independent")){
                        independent.setChecked(true);
                    }
                    else if (quality.equals("Intellectual")){
                        intellectual.setChecked(true);
                    }
                    else if (quality.equals("Introvert")){
                        introvert.setChecked(true);
                    }
                    else if (quality.equals("Kind")){
                        kind.setChecked(true);
                    }
                    else if (quality.equals("Loyal")){
                        loyal.setChecked(true);
                    }
                    else if (quality.equals("Meditation")){
                        meditation.setChecked(true);
                    }
                    else if (quality.equals("Moody")){
                        moody.setChecked(true);
                    }
                    else if (quality.equals("Motivated")){
                        motivated.setChecked(true);
                    }
                    else if (quality.equals("MovieBuff")){
                        moviebuff.setChecked(true);
                    }
                    else if (quality.equals("MusicLover")){
                        musiclover.setChecked(true);
                    }
                    else if (quality.equals("Mysterious")){
                        mysterious.setChecked(true);
                    }
                    else if (quality.equals("NatureLover")){
                        naturelover.setChecked(true);
                    }
                    else if (quality.equals("NightOwl")){
                        nightowl.setChecked(true);
                    }
                    else if (quality.equals("OpenMinded")){
                        openminded.setChecked(true);
                    }
                    else if (quality.equals("Optimistic")){
                        optimistic.setChecked(true);
                    }
                    else if (quality.equals("PartyHopper")){
                        partyhopper.setChecked(true);
                    }
                    else if (quality.equals("Reader")){
                        reader.setChecked(true);
                    }
                    else if (quality.equals("Religious")){
                        religious.setChecked(true);
                    }
                    else if (quality.equals("Romantic")){
                        romantic.setChecked(true);
                    }
                    else if (quality.equals("Sarcastic")){
                        sarcastic.setChecked(true);
                    }
                    else if (quality.equals("Sexy")){
                        sexy.setChecked(true);
                    }
                    else if (quality.equals("Shopaholic")){
                        shopaholic.setChecked(true);
                    }
                    else if (quality.equals("Shy")){
                        shy.setChecked(true);
                    }
                    else if (quality.equals("Simple")){
                        simple.setChecked(true);
                    }
                    else if (quality.equals("Spiritual")){
                        spiritual.setChecked(true);
                    }
                    else if (quality.equals("SportsEnthusiast")){
                        sportsenthusiast.setChecked(true);
                    }
                    else if (quality.equals("Stylish")){
                        stylish.setChecked(true);
                    }
                    else if (quality.equals("Talkative")){
                        talkative.setChecked(true);
                    }
                    else if (quality.equals("Techie")){
                        techie.setChecked(true);
                    }
                    else if (quality.equals("Traveller")){
                        traveller.setChecked(true);
                    }
                    else if (quality.equals("Workaholic")){
                        workaholic.setChecked(true);
                    }
                    else if (quality.equals("Writer")){
                        writer.setChecked(true);
                    }
                    else if (quality.equals("Yoga")){
                        yoga.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    if(selectedQualities.size()<10){
                        selectedQualities.add(buttonView.getText().toString());
                        Log.i("quality is",buttonView.getText().toString());
                        Log.i("size", String.valueOf(selectedQualities.size()));
                    }
                    else {
                        buttonView.setChecked(false);
                        selectedQualities.remove(buttonView.getText().toString());
                        Toast.makeText(getContext(), "please select only 10 qualities",Toast.LENGTH_SHORT).show();
                        Log.i("size", String.valueOf(selectedQualities.size()));
                    }

                }
                else {
                    selectedQualities.remove(buttonView.getText().toString());
                    Log.i("size", String.valueOf(selectedQualities.size()));

                }
            }
        };

        adventorous.setOnCheckedChangeListener(checkedChangeListener);
        ambitious.setOnCheckedChangeListener(checkedChangeListener);
        ambivert.setOnCheckedChangeListener(checkedChangeListener);
        artist.setOnCheckedChangeListener(checkedChangeListener);
        calm.setOnCheckedChangeListener(checkedChangeListener);
        caring.setOnCheckedChangeListener(checkedChangeListener);
        chailover.setOnCheckedChangeListener(checkedChangeListener);
        charming.setOnCheckedChangeListener(checkedChangeListener);
        cheerful.setOnCheckedChangeListener(checkedChangeListener);
        chocaholic.setOnCheckedChangeListener(checkedChangeListener);
        coffelover.setOnCheckedChangeListener(checkedChangeListener);
        creative.setOnCheckedChangeListener(checkedChangeListener);
        curious.setOnCheckedChangeListener(checkedChangeListener);
        dancer.setOnCheckedChangeListener(checkedChangeListener);
        doglover.setOnCheckedChangeListener(checkedChangeListener);
        dreamer.setOnCheckedChangeListener(checkedChangeListener);
        emotional.setOnCheckedChangeListener(checkedChangeListener);
        energetic.setOnCheckedChangeListener(checkedChangeListener);
        entertainer.setOnCheckedChangeListener(checkedChangeListener);
        entrepreneur.setOnCheckedChangeListener(checkedChangeListener);
        extrovert.setOnCheckedChangeListener(checkedChangeListener);
        faithful.setOnCheckedChangeListener(checkedChangeListener);
        fitnessfreak.setOnCheckedChangeListener(checkedChangeListener);
        foodie.setOnCheckedChangeListener(checkedChangeListener);
        friendly.setOnCheckedChangeListener(checkedChangeListener);
        funny.setOnCheckedChangeListener(checkedChangeListener);
        gadgetlover.setOnCheckedChangeListener(checkedChangeListener);
        gameaddict.setOnCheckedChangeListener(checkedChangeListener);
        geek.setOnCheckedChangeListener(checkedChangeListener);
        gym.setOnCheckedChangeListener(checkedChangeListener);
        humorous.setOnCheckedChangeListener(checkedChangeListener);
        independent.setOnCheckedChangeListener(checkedChangeListener);
        intellectual.setOnCheckedChangeListener(checkedChangeListener);
        introvert.setOnCheckedChangeListener(checkedChangeListener);
        kind.setOnCheckedChangeListener(checkedChangeListener);
        loyal.setOnCheckedChangeListener(checkedChangeListener);
        meditation.setOnCheckedChangeListener(checkedChangeListener);
        moody.setOnCheckedChangeListener(checkedChangeListener);
        motivated.setOnCheckedChangeListener(checkedChangeListener);
        moviebuff.setOnCheckedChangeListener(checkedChangeListener);
        musiclover.setOnCheckedChangeListener(checkedChangeListener);
        mysterious.setOnCheckedChangeListener(checkedChangeListener);
        naturelover.setOnCheckedChangeListener(checkedChangeListener);
        nightowl.setOnCheckedChangeListener(checkedChangeListener);
        openminded.setOnCheckedChangeListener(checkedChangeListener);
        optimistic.setOnCheckedChangeListener(checkedChangeListener);
        partyhopper.setOnCheckedChangeListener(checkedChangeListener);
        reader.setOnCheckedChangeListener(checkedChangeListener);
        religious.setOnCheckedChangeListener(checkedChangeListener);
        romantic.setOnCheckedChangeListener(checkedChangeListener);
        sarcastic.setOnCheckedChangeListener(checkedChangeListener);
        sexy.setOnCheckedChangeListener(checkedChangeListener);
        shopaholic.setOnCheckedChangeListener(checkedChangeListener);
        shy.setOnCheckedChangeListener(checkedChangeListener);
        simple.setOnCheckedChangeListener(checkedChangeListener);
        spiritual.setOnCheckedChangeListener(checkedChangeListener);
        sportsenthusiast.setOnCheckedChangeListener(checkedChangeListener);
        stylish.setOnCheckedChangeListener(checkedChangeListener);
        talkative.setOnCheckedChangeListener(checkedChangeListener);
        techie.setOnCheckedChangeListener(checkedChangeListener);
        traveller.setOnCheckedChangeListener(checkedChangeListener);
        workaholic.setOnCheckedChangeListener(checkedChangeListener);
        writer.setOnCheckedChangeListener(checkedChangeListener);
        yoga.setOnCheckedChangeListener(checkedChangeListener);

        save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQualities.size()<4){
                    Toast.makeText(getContext(),"Please select atleast 4 qualities",Toast.LENGTH_SHORT).show();
                    save.setVisibility(View.VISIBLE);
                    return;
                }

                FirebaseDatabase db;
                db = FirebaseDatabase.getInstance();
                db.getReference().child("UserQualitiesData").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("self");

                        for(int i = 0; i<selectedQualities.size();i++){
                            HashMap<String, Object> quality= new HashMap<>();
                            quality.put("quality",selectedQualities.get(i));
                            databaseReference.push().setValue(quality).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ProfileFragment profileFragment = new ProfileFragment();

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_frame,profileFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            });
                        }
                    }
                });
            }
        });


        return view;
    }
}