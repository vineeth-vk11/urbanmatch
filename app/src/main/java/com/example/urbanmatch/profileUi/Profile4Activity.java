package com.example.urbanmatch.profileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urbanmatch.MainActivity;
import com.example.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Profile4Activity extends AppCompatActivity {

    EditText mobileNumber, Aadhar, OTP;
    Button sendOTP, verifyOTP;
    String uid;
    String codeSent;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile4);

        mobileNumber = findViewById(R.id.Mobile_number_edit);
        Aadhar = findViewById(R.id.Aadhar_edit);
        OTP = findViewById(R.id.OTP_edit);

        sendOTP = findViewById(R.id.button);
        verifyOTP = findViewById(R.id.buttonForOTP);

        firebaseAuth = FirebaseAuth.getInstance();

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadhar = Aadhar.getText().toString().trim();

                if(TextUtils.isEmpty(aadhar)){
                    Toast.makeText(Profile4Activity.this,"Please enter your Aadhar",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(TextUtils.isDigitsOnly(aadhar))){
                    Toast.makeText(Profile4Activity.this,"Please enter correct Aadhar Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(aadhar.length() != 12){
                    Toast.makeText(Profile4Activity.this,"Please enter correct Aadhar Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                sendOTP();

            }
        });

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTPSign();
            }
        });
    }

    private void verifyOTPSign(){
        String code = OTP.getText().toString();
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(codeSent,code);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Profile4Activity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void sendOTP(){

        String phoneNumber = mobileNumber.getText().toString();

        if(phoneNumber.isEmpty()){
            Toast.makeText(Profile4Activity.this,"Please select Mobile Number",Toast.LENGTH_SHORT).show();
            return;
        }

        if(phoneNumber.length() != 10){
            Toast.makeText(Profile4Activity.this,"Please enter correct phone number",Toast.LENGTH_SHORT).show();
            return;
        }

        String number = "+91" + phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            OTP.setVisibility(View.VISIBLE);
            verifyOTP.setVisibility(View.VISIBLE);
        }
    };
}