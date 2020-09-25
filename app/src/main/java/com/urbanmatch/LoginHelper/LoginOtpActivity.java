package com.urbanmatch.LoginHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.urbanmatch.MainActivity;
import com.urbanmatch.R;
import com.urbanmatch.profileUi.Profile1Activity;
import com.urbanmatch.profileUi.Profile3Activity;
import com.urbanmatch.profileUi.Profile4Activity;
import com.urbanmatch.profileUi.QualitiesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginOtpActivity extends AppCompatActivity {

    EditText otp;
    String sentOTP;

    Button validate;

    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        sentOTP = intent.getStringExtra("otp");

        otp = findViewById(R.id.login_otp_edit);
        validate = findViewById(R.id.validate_button);

        progressBar = findViewById(R.id.progressBar7);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                validate.setEnabled(false);
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(LoginOtpActivity.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    validate.setEnabled(true);
                }
                else {
                    verifyOTPSign();
                }
            }
        });
    }

    private void verifyOTPSign(){
        String code = otp.getText().toString();
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(sentOTP,code);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser != null) {
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (!documentSnapshot.exists()) {
                                    Intent intent = new Intent(LoginOtpActivity.this, Profile1Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String profileStatus = documentSnapshot.getString("profileStatus");

                                    if (profileStatus.equals("1")) {
                                        Intent intent = new Intent(LoginOtpActivity.this, QualitiesActivity.class);
                                        intent.putExtra("type", "self");
                                        startActivity(intent);
                                        finish();
                                    } else if (profileStatus.equals("2")) {
                                        Intent intent = new Intent(LoginOtpActivity.this, Profile3Activity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (profileStatus.equals("3")) {
                                        Intent intent = new Intent(LoginOtpActivity.this, QualitiesActivity.class);
                                        intent.putExtra("type", "required");
                                        startActivity(intent);
                                        finish();
                                    } else if (profileStatus.equals("4")) {
                                        Intent intent = new Intent(LoginOtpActivity.this, Profile4Activity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (profileStatus.equals("5")) {
                                        Intent intent = new Intent(LoginOtpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(LoginOtpActivity.this, "Please enter correct otp", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    validate.setEnabled(true);
                    return;
                }
            }
        });
    }
}