package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagePress extends AppCompatActivity {

    TextView title;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_press);

        getSupportActionBar().setTitle("Images");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = findViewById(R.id.imageTextView);
        image = findViewById(R.id.imageView);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        String sTitle = getIntent().getStringExtra("title");

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        title.setText(sTitle);
        image.setImageBitmap(bitmap);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
