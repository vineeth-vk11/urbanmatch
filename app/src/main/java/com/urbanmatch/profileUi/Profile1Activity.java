package com.urbanmatch.profileUi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.urbanmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Profile1Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button button;
    Button upload;
    EditText txtname, txtdob, txtLastName;
    RadioGroup radioGroup;
    RadioButton selectedGender;
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

    ArrayList<String> CITIES = new ArrayList<>();
    ArrayList<String > NATIVESTATES = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        NATIVESTATES.add("Andhra Pradesh");
        NATIVESTATES.add("Andaman and Nicobar");
        NATIVESTATES.add("Arunachal Pradesh");
        NATIVESTATES.add("Assam");
        NATIVESTATES.add("Bihar");
        NATIVESTATES.add("Chandigarh");
        NATIVESTATES.add("Chhattisgarh");
        NATIVESTATES.add("Delhi");
        NATIVESTATES.add("Goa");
        NATIVESTATES.add("Gujarat");
        NATIVESTATES.add("Haryana");
        NATIVESTATES.add("Himachal Pradesh");
        NATIVESTATES.add("Jammu and Kashmir");
        NATIVESTATES.add("Jharkhand");
        NATIVESTATES.add("Karnataka");
        NATIVESTATES.add("Kerala");
        NATIVESTATES.add("Ladakh");
        NATIVESTATES.add("Madhya Pradesh");
        NATIVESTATES.add("Maharashtra");
        NATIVESTATES.add("Manipur");
        NATIVESTATES.add("Meghalaya");
        NATIVESTATES.add("Mizoram");
        NATIVESTATES.add("Nagaland");
        NATIVESTATES.add("Odisha");
        NATIVESTATES.add("puducherry");
        NATIVESTATES.add("Punjab");
        NATIVESTATES.add("Rajasthan");
        NATIVESTATES.add("Sikkim");
        NATIVESTATES.add("Tamil Nadu");
        NATIVESTATES.add("Telangana");
        NATIVESTATES.add("Tripura");
        NATIVESTATES.add("Uttar Pradesh");
        NATIVESTATES.add("Uttarakhand");
        NATIVESTATES.add("West Bengal");

        String[] CURRENTSTATES = new String[]{"Current state1", "Current state2"};

        CITIES.add("Mumbai");
        CITIES.add("Delhi");
        CITIES.add("Bangalore");
        CITIES.add("Hyderabad");
        CITIES.add("Ahmedabad");
        CITIES.add("Chennai");
        CITIES.add("Kolkata");
        CITIES.add("Surat");
        CITIES.add("Pune");
        CITIES.add("Jaipur");
        CITIES.add("Lucknow");
        CITIES.add("Kanpur");
        CITIES.add("Nagpur");
        CITIES.add("Indore");
        CITIES.add("Thane");
        CITIES.add("Bhopal");
        CITIES.add("Visakhapatnam");
        CITIES.add("Pimpri & Chinchwad");
        CITIES.add("Patna");
        CITIES.add("Vadodara");
        CITIES.add("Ghaziabad");
        CITIES.add("Ludhiana");
        CITIES.add("Agra");
        CITIES.add("Nashik");
        CITIES.add("Faridabad");
        CITIES.add("Meerut");
        CITIES.add("Rajkot");
        CITIES.add("Kalyan & Dombivali");
        CITIES.add("Vasai Virar");
        CITIES.add("Varanasi");
        CITIES.add("Srinagar");
        CITIES.add("Aurangabad");
        CITIES.add("Dhanbad");
        CITIES.add("Amritsar");
        CITIES.add("Navi Mumbai");
        CITIES.add("Allahabad");
        CITIES.add("Ranchi");
        CITIES.add("Haora");
        CITIES.add("Coimbatore");
        CITIES.add("Jabalpur");
        CITIES.add("Gwalior");
        CITIES.add("Vijayawada");
        CITIES.add("Jodhpur");
        CITIES.add("Madurai");
        CITIES.add("Raipur");
        CITIES.add("Kota");
        CITIES.add("Guwahati");
        CITIES.add("Chandigarh");
        CITIES.add("Solapur");
        CITIES.add("Hubli and Dharwad");
        CITIES.add("Bareilly");
        CITIES.add("Moradabad");
        CITIES.add("Mysore");
        CITIES.add("Gurgaon");
        CITIES.add("Aligarh");
        CITIES.add("Jalandhar");
        CITIES.add("Tiruchirappalli");
        CITIES.add("Bhubaneswar");
        CITIES.add("Salem");
        CITIES.add("Mira and Bhayander");
        CITIES.add("Thiruvananthapuram");
        CITIES.add("Bhiwandi");
        CITIES.add("Saharanpur");
        CITIES.add("Gorakhpur");
        CITIES.add("Guntur");
        CITIES.add("Bikaner");
        CITIES.add("Amravati");
        CITIES.add("Noida");
        CITIES.add("Jamshedpur");
        CITIES.add("Bhilai Nagar");
        CITIES.add("Warangal");
        CITIES.add("Cuttack");
        CITIES.add("Firozabad");
        CITIES.add("Kochi");
        CITIES.add("Bhavnagar");
        CITIES.add("Dehradun");
        CITIES.add("Durgapur");
        CITIES.add("Asansol");
        CITIES.add("Nanded Waghala");
        CITIES.add("Kolapur");
        CITIES.add("Ajmer");
        CITIES.add("Gulbarga");
        CITIES.add("Jamnagar");
        CITIES.add("Ujjain");
        CITIES.add("Loni");
        CITIES.add("Siliguri");
        CITIES.add("Jhansi");
        CITIES.add("Ulhasnagar");
        CITIES.add("Nellore");
        CITIES.add("Jammu");
        CITIES.add("Sangli Miraj Kupwad");
        CITIES.add("Belgaum");
        CITIES.add("Mangalore");
        CITIES.add("Ambattur");
        CITIES.add("Tirunelveli");
        CITIES.add("Malegoan");
        CITIES.add("Gaya");
        CITIES.add("Jalgaon");
        CITIES.add("Udaipur");
        CITIES.add("Maheshtala");

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
                CITIES
        );

        final AutoCompleteTextView nativeStates = findViewById(R.id.state_dropdown);
        final AutoCompleteTextView cities = findViewById(R.id.city_dropdown);

        nativeStates.setAdapter(adapter);
        cities.setAdapter(adapter2);

        profilepic = findViewById(R.id.profilepic);
        txtname = findViewById(R.id.name_edit);
        txtdob = findViewById(R.id.dob_edit);
        radioGroup = findViewById(R.id.radioGroup);
        txtLastName = findViewById(R.id.last_name_edit);

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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString().trim();
                String lastName = txtLastName.getText().toString().trim();
                String dob = txtdob.getText().toString().trim();
                String gender = String.valueOf(getGender());
                String nativeState = nativeStates.getText().toString().trim();
                String currentState = "Null";
                String city = cities.getText().toString().trim();


                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Profile1Activity.this,"Please enter your first name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(Profile1Activity.this,"Please enter your last name",Toast.LENGTH_SHORT).show();
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
                else if(! NATIVESTATES.contains(nativeState)){
                    Toast.makeText(Profile1Activity.this,"Please select a state from drop down",Toast.LENGTH_SHORT).show();
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
                else if(! CITIES.contains(city)){
                    Toast.makeText(Profile1Activity.this,"Please select a city from drop down",Toast.LENGTH_SHORT).show();
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
                intent1.putExtra("lastName",lastName);
                intent1.putExtra("dob",dob);
                intent1.putExtra("gender",gender);
                intent1.putExtra("nativeState",nativeState);
                intent1.putExtra("currentState",currentState);
                intent1.putExtra("city",city);
                intent1.putExtra("userId",uid);
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

    private int getGender(){
        int genderId = radioGroup.getCheckedRadioButtonId();
        selectedGender = findViewById(genderId);
        String gender = selectedGender.getText().toString();

        if(gender.equals("Female")){
            return 2;
        }
        else {
            return 1;
        }

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