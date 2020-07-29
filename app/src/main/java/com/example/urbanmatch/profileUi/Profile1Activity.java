package com.example.urbanmatch.profileUi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.urbanmatch.MainActivity;
import com.example.urbanmatch.R;
import com.example.urbanmatch.RegisterActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Profile1Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button button;
    Button upload;
    EditText txtname, txtdob;
    RadioGroup radioGroup;
    ImageButton imageButton;

    String age;

    int Date;
    int Month;
    int Year;

    private ImageView profilepic;
    private static final int PICK_IMAGE = 1;
    Uri uri;

    String uid;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        final Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        String[] NATIVESTATES = new String[]{"Native state1", "Native state2"};
        String[] CURRENTSTATES = new String[]{"Current state1", "Current state2"};
        final String[] CITIESSTATE1 = new String[]{"city1", "city2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                NATIVESTATES
        );

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                CURRENTSTATES
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.dropdown_menu_popup_item,
                CITIESSTATE1
        );

        final AutoCompleteTextView nativeStates = findViewById(R.id.state_dropdown);
        final AutoCompleteTextView cities = findViewById(R.id.city_dropdown);
//        final AutoCompleteTextView currentStates = findViewById(R.id.current_state_dropdown);

        nativeStates.setAdapter(adapter);
//        currentStates.setAdapter(adapter1);
        cities.setAdapter(adapter2);

        profilepic = findViewById(R.id.profilepic);
        txtname = findViewById(R.id.name_edit);
        txtdob = findViewById(R.id.dob_edit);
        radioGroup = findViewById(R.id.radioGroup);

        upload = findViewById(R.id.upload);
        button = findViewById(R.id.next);

        db = FirebaseFirestore.getInstance();

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        txtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString().trim();
                String dob = txtdob.getText().toString().trim();
                int igender = radioGroup.getCheckedRadioButtonId();
                String gender = String.valueOf(igender);
                String nativeState = nativeStates.getText().toString().trim();
                String currentState = "Null";
                String city = cities.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Profile1Activity.this,"Please enter your name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(dob)){
                    Toast.makeText(Profile1Activity.this,"Please enter your date of birth",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!validateJavaDate(dob)){
                    Toast.makeText(Profile1Activity.this,"Please enter proper date",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Integer.parseInt(age)<24){
                    Toast.makeText(Profile1Activity.this,"Age cannot be less than 24",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Integer.parseInt(age)>40){
                    Toast.makeText(Profile1Activity.this,"Age cannot be more than 40",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(gender)){
                    Toast.makeText(Profile1Activity.this,"Please enter your gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(nativeState)){
                    Toast.makeText(Profile1Activity.this,"Please enter your Native State",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(currentState)){
                    Toast.makeText(Profile1Activity.this,"Please enter your Current State",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(city)){
                    Toast.makeText(Profile1Activity.this,"Please enter your City",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(uri == null){
                    Toast.makeText(Profile1Activity.this,"Please upload your image",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent1 = new Intent(Profile1Activity.this,Profile2Activity.class);
                intent1.putExtra("name",name);
                intent1.putExtra("dob",dob);
                intent1.putExtra("gender",gender);
                intent1.putExtra("nativeState",nativeState);
                intent1.putExtra("currentState",currentState);
                intent1.putExtra("city",city);
                intent1.putExtra("userId",userId);
                intent1.putExtra("age",age);
                intent1.putExtra("imageUri",uri.toString());
                startActivity(intent1);


            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(Profile1Activity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
            else {
                startCrop(imageuri);
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                uri = result.getUri();
                profilepic.setImageURI(result.getUri());

            }
        }
    }

    private void startCrop(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1,1)
                .start(this);

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONDAY),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        Date = dayOfMonth;
        Month = month;
        Year = year;
        age = getAge(Year, Month, Date);
        txtdob.setText(date);
    }

    public static boolean validateJavaDate(String strDate)
    {
        if (strDate.trim().equals(""))
        {
            return false;
        }
        else
        {

            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);
            try
            {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println(strDate+" is valid date format");
            }
            catch (ParseException e)
            {
                return false;
            }
            return true;
        }
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}