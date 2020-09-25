package com.urbanmatch.profileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QualitiesActivity extends AppCompatActivity {

    private Chip adventorous, ambitious, ambivert, artist, calm, caring,
    chailover, charming, cheerful, chocaholic, coffelover, creative, curious,
    dancer, doglover, dreamer, emotional, energetic, entertainer, entrepreneur,
    extrovert, faithful, fitnessfreak, foodie, friendly, funny, gadgetlover, gameaddict,
    geek, gym, humorous, independent, intellectual, introvert, kind, loyal, meditation, moody, motivated,
    moviebuff, musiclover, mysterious, naturelover, nightowl, openminded, optimistic, partyhopper, reader, religious,
    romantic, sarcastic, sexy, shopaholic, shy, simple, spiritual, sportsenthusiast, stylish, talkative, techie, traveller, workaholic, writer, yoga;

    ChipGroup chipGroup;

    private ArrayList<String> selectedQualities;

    Button save;

    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualities);

        selectedQualities = new ArrayList<>();

        chipGroup = findViewById(R.id.qualities_group);

        Intent intent = getIntent();
        final String saveTo = intent.getStringExtra("type");

        info = findViewById(R.id.info);

        if(saveTo.equals("self")){
            info.setText("Describe yourself in atleast 4 words");
        }
        else if(saveTo.equals("required")){
            info.setText("Describe your partner in atlease 4 words");
        }

        adventorous = findViewById(R.id.adventurous);
        ambitious = findViewById(R.id.ambitious);
        ambivert = findViewById(R.id.ambivert);
        artist = findViewById(R.id.artist);
        calm = findViewById(R.id.calm);
        caring = findViewById(R.id.caring);
        chailover = findViewById(R.id.chailover);
        charming = findViewById(R.id.charming);
        cheerful = findViewById(R.id.cheerful);
        chocaholic = findViewById(R.id.chocaholic);
        coffelover = findViewById(R.id.coffelover);
        creative = findViewById(R.id.creative);
        curious = findViewById(R.id.curious);
        dancer = findViewById(R.id.dance);
        doglover = findViewById(R.id.doglover);
        dreamer = findViewById(R.id.dreamer);
        emotional = findViewById(R.id.emotional);
        energetic = findViewById(R.id.energetic);
        entertainer = findViewById(R.id.entertainer);
        entrepreneur = findViewById(R.id.entertainer);
        extrovert = findViewById(R.id.extrovert);
        faithful = findViewById(R.id.faithful);
        fitnessfreak = findViewById(R.id.fitnessfreak);
        foodie = findViewById(R.id.foodie);
        friendly = findViewById(R.id.friendly);
        funny = findViewById(R.id.funny);
        gadgetlover = findViewById(R.id.gadgetlover);
        gameaddict = findViewById(R.id.gameaddict);
        geek = findViewById(R.id.geek);
        gym = findViewById(R.id.gym);
        humorous = findViewById(R.id.humorous);
        independent = findViewById(R.id.independent);
        intellectual = findViewById(R.id.intellectual);
        introvert = findViewById(R.id.introvert);
        kind = findViewById(R.id.kind);
        loyal = findViewById(R.id.loyal);
        meditation = findViewById(R.id.meditation);
        moody = findViewById(R.id.moody);
        motivated = findViewById(R.id.motivated);
        moviebuff = findViewById(R.id.moviebuff);
        musiclover = findViewById(R.id.musiclover);
        mysterious = findViewById(R.id.mysterious);
        naturelover = findViewById(R.id.naturelover);
        nightowl = findViewById(R.id.nightowl);
        openminded = findViewById(R.id.openminded);
        optimistic = findViewById(R.id.optimistic);
        partyhopper = findViewById(R.id.partyhopper);
        reader = findViewById(R.id.reader);
        religious = findViewById(R.id.religious);
        romantic = findViewById(R.id.romantic);
        sarcastic = findViewById(R.id.sarcastic);
        sexy = findViewById(R.id.sexy);
        shopaholic = findViewById(R.id.shopaholic);
        shy = findViewById(R.id.shy);
        simple = findViewById(R.id.simple);
        spiritual = findViewById(R.id.spiritual);
        sportsenthusiast = findViewById(R.id.sportsenthusiast);
        stylish = findViewById(R.id.stylish);
        talkative = findViewById(R.id.talkative);
        techie = findViewById(R.id.techie);
        traveller = findViewById(R.id.traveller);
        workaholic = findViewById(R.id.workaholic);
        writer = findViewById(R.id.writer);
        yoga = findViewById(R.id.yoga);


        if(selectedQualities.size()>=10){
            chipGroup.setClickable(false);
        }

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
                        Toast.makeText(QualitiesActivity.this, "please select only 10 qualities",Toast.LENGTH_SHORT).show();
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

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.INVISIBLE);
                if(selectedQualities.size()<4){
                    Toast.makeText(QualitiesActivity.this,"Please select atleast 4 qualities",Toast.LENGTH_SHORT).show();
                    save.setVisibility(View.VISIBLE);
                    return;
                }

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase db;
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserQualitiesData").
                        child(uid).child(saveTo);
                for(int i=0; i< selectedQualities.size(); i++){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("quality",selectedQualities.get(i));
                    databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(saveTo.equals("self")){

                                final Map<String, Object> status = new HashMap<>();
                                status.put("profileStatus","2");

                                FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .update(status);
                                Intent intent = new Intent(QualitiesActivity.this,Profile3Activity.class);
                                startActivity(intent);
                            }
                            else if(saveTo.equals("required")){

                                final Map<String, Object> status = new HashMap<>();
                                status.put("profileStatus","4");

                                FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .update(status);

                                Intent intent = new Intent(QualitiesActivity.this,Profile4Activity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });





    }
}