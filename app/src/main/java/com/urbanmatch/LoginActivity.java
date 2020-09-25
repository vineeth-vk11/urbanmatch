package com.urbanmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText txtemail, txtpassword;
    Button login;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    Button goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            if(firebaseUser.getPhoneNumber() == null){
                Intent intent = new Intent(LoginActivity.this,Profile4Activity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

        txtemail = findViewById(R.id.email_edit_login);
        txtpassword = findViewById(R.id.password_edit_login);


        login = findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        goToRegister = findViewById(R.id.buttonForLogin);
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!email.contains(".")){
                    Toast.makeText(LoginActivity.this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!email.contains("@")){
                    Toast.makeText(LoginActivity.this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
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
                                                Intent intent = new Intent(LoginActivity.this, Profile1Activity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                String profileStatus = documentSnapshot.getString("profileStatus");

                                                if (profileStatus.equals("1")) {
                                                    Intent intent = new Intent(LoginActivity.this, QualitiesActivity.class);
                                                    intent.putExtra("type", "self");
                                                    startActivity(intent);
                                                    finish();
                                                } else if (profileStatus.equals("2")) {
                                                    Intent intent = new Intent(LoginActivity.this, Profile3Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (profileStatus.equals("3")) {
                                                    Intent intent = new Intent(LoginActivity.this, QualitiesActivity.class);
                                                    intent.putExtra("type", "required");
                                                    startActivity(intent);
                                                    finish();
                                                } else if (profileStatus.equals("4")) {
                                                    Intent intent = new Intent(LoginActivity.this, Profile4Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (profileStatus.equals("5")) {
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

    }
}