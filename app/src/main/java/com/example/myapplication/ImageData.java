package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageData extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_data);

        recyclerView = findViewById(R.id.recyclerviewimage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Image");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ImageMember, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ImageMember, ViewHolder>(
                        ImageMember.class,
                        R.layout.image,
                        ViewHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, ImageMember imageMember, int i) {
                        viewHolder.setDetails(getApplicationContext(), imageMember.getTitle(), imageMember.getImage());
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOneClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView title = view.findViewById(R.id.imageText);
                                ImageView image = view.findViewById(R.id.image);

                                String mTitle = title.getText().toString();
                                Drawable drawable = image.getDrawable();
                                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                                Intent intent = new Intent(view.getContext(), ImagePress.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();

                                intent.putExtra("image", bytes);
                                intent.putExtra("title", mTitle);

                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
