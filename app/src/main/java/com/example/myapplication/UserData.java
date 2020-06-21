package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserData extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Data"); // another way of giving path of table name
    }

    @Override
    protected void onStart() {
        super.onStart();

        // FirebaseRecyclerAdapter uses firebaseui dependency that's why we added this dependency
        FirebaseRecyclerAdapter<User, Holder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, Holder>(
                        User.class,
                        R.layout.data,
                        Holder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(Holder holder, User user, int i) {
                        holder.setView(getApplicationContext(), user.getName(), user.getGender());
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
