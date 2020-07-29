package com.example.urbanmatch;

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

import com.example.urbanmatch.profileUi.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText txtemail, txtpassword, txtcpassword;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar2);

        Button login;

        txtemail = findViewById(R.id.email_edit);
        txtpassword = findViewById(R.id.password_edit);
        txtcpassword = findViewById(R.id.confirm_password_edit);

        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String cpassword = txtcpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }

                else if(!email.contains(".")){
                    Toast.makeText(RegisterActivity.this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
                }

                else if(!email.contains("@")){
                    Toast.makeText(RegisterActivity.this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cpassword)){
                    Toast.makeText(RegisterActivity.this,"Please enter confirm password",Toast.LENGTH_SHORT).show();
                }

                else if(password.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password length is too short",Toast.LENGTH_SHORT).show();
                }

                else if(!password.equals(cpassword)){
                    Toast.makeText(RegisterActivity.this,"confirm password does not match with password",Toast.LENGTH_SHORT).show();
                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    register.setVisibility(View.INVISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        String userId = firebaseAuth.getCurrentUser().getUid();
                                        Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                                        intent.putExtra("userId",userId);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        login = findViewById(R.id.buttonForLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                }
        });

    }
}