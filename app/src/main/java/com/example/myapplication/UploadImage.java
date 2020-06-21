package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadImage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private EditText buttonTitle;
    private ProgressBar progressBar;
    private ImageView imageView;

    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        buttonChoose = findViewById(R.id.imageChoose);
        buttonUpload = findViewById(R.id.uploadButton);
        buttonTitle = findViewById(R.id.uploadText);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageUpload);

        storageReference = FirebaseStorage.getInstance().getReference().child("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Image");

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenImageChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploadfile();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void OpenImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK
                || data != null || data.getData() != null
        ){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    //upload image function
    private void Uploadfile(){
        if (imageUri != null){
            StorageReference fileStorage = storageReference.child(System.currentTimeMillis()
            + " " + getFileExt(imageUri));
            fileStorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadImage.this, "Uploading Successful", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(buttonTitle.getText().toString().trim(),
                            taskSnapshot.getUploadSessionUri().toString());
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(upload);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadImage.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(UploadImage.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
