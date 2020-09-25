package com.urbanmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.urbanmatch.profileUi.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText txtemail, txtpassword, txtcpassword;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    ArrayList<String > emails = new ArrayList<>();

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
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        emails.add("gmail.com");
        emails.add("yahoo.com");
        emails.add("hotmail.com");
        emails.add("taol.com");
        emails.add("hotmail.co.uk");
        emails.add("hotmail.fr");
        emails.add("msn.com");
        emails.add("yahoo.fr");
        emails.add("wanadoo.fr");
        emails.add("orange.fr");
        emails.add("comcast.net");
        emails.add("yahoo.co.uk");
        emails.add("yahoo.com.br");
        emails.add("yahoo.co.in");
        emails.add("live.com");
        emails.add("rediffmail.com");
        emails.add("free.fr");
        emails.add("gmx.de");
        emails.add("web.de");
        emails.add("yandex.ru");
        emails.add("ymail.com");
        emails.add("libero.it");
        emails.add("outlook.com");
        emails.add("tuol.com.br");
        emails.add("bol.com.br");
        emails.add("mail.ru");
        emails.add("tcox.net");
        emails.add("hotmail.it");
        emails.add("sbcglobal.net");
        emails.add("sfr.fr");
        emails.add("live.fr");
        emails.add("verizon.net");
        emails.add("live.co.uk");
        emails.add("googlemail.com");
        emails.add("yahoo.es");
        emails.add("ig.com.br");
        emails.add("live.nl");
        emails.add("tbigpond.com");
        emails.add("terra.com.br");
        emails.add("yahoo.it");
        emails.add("tneuf.fr");
        emails.add("yahoo.de");
        emails.add("alice.it");
        emails.add("rocketmail.com");
        emails.add("tatt.net");
        emails.add("tlaposte.net");
        emails.add("facebook.com");
        emails.add("bellsouth.net");
        emails.add("yahoo.in");
        emails.add("hotmail.es");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String cpassword = txtcpassword.getText().toString().trim();

                int x = 0;

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

                    for(int i = 0; i<emails.size();i++){
                        if(email.endsWith(emails.get(i))){
                            x += 1;
                            break;
                        }
                        else{
                            Log.i("not","found");
                        }
                    }

                    if(x == 0){
                        Toast.makeText(RegisterActivity.this,"Please enter a proper email",Toast.LENGTH_SHORT).show();
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
                                            finish();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                        else {
                                            Toast.makeText(RegisterActivity.this,"This mail Id is already registered with us. Please login",Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            register.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });

                    }

                }

            }
        });

        login = findViewById(R.id.buttonForLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                }
        });

    }
}