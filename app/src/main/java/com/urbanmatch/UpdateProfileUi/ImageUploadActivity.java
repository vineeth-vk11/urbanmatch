package com.urbanmatch.UpdateProfileUi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.urbanmatch.MainActivity;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.UUID;


public class ImageUploadActivity extends AppCompatActivity {

    private ImageView profilepic;
    private static final int PICK_IMAGE = 1;
    Uri uri;

    Button upload;
    Button save;

    String uid;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        progressBar = findViewById(R.id.progressBar4);

        profilepic = findViewById(R.id.upload_image_view);
        upload = findViewById(R.id.Upload);
        save = findViewById(R.id.save);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(ImageUploadActivity.this);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if(uri==null){
                    Toast.makeText(ImageUploadActivity.this,"Please select an image to upload",Toast.LENGTH_SHORT).show();
                    save.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                final StorageReference storageReference1 = storageReference.child(String.valueOf(uid) + "/" + UUID.randomUUID());
                storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Log.i("Download Url", String.valueOf(downloadUrl));
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("photos").child(uid);
                                HashMap<String, Object> image = new HashMap<>();
                                image.put("imagePath", String.valueOf(downloadUrl));
                                image.put("imageNumber", "0");
                                databaseReference.push().setValue(image);
                            }
                        });

                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(ImageUploadActivity.this, MainActivity.class);
                        startActivity(intent);

                    }

                });
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
}
