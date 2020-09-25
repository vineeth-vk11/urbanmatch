package com.urbanmatch.profileUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.urbanmatch.MainActivity;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile4Activity extends AppCompatActivity {

    EditText txtAadhar;
    EditText txtEmail;

    Button next;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    ArrayList<String > emails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile4);

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

        txtAadhar = findViewById(R.id.Aadhar_edit);
        txtEmail = findViewById(R.id.email_edit);

        next = findViewById(R.id.button);

        firebaseAuth = FirebaseAuth.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String aadhar = txtAadhar.getText().toString().trim();

                int x = 0;

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Profile4Activity.this, "Enter your Aadhar number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(aadhar)){
                    Toast.makeText(Profile4Activity.this, "Enter your email", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Profile4Activity.this,"Please enter a proper email",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        HashMap<String, Object> aadharMap = new HashMap<>();
                        aadharMap.put("Aadhar",aadhar);
                        aadharMap.put("email",email);

                        aadharMap.put("profileStatus","5");
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(aadharMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(Profile4Activity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    }

                }
            }
        });
    }
}