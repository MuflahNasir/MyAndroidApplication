package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    View view;
    public Holder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }
    public void setView(Context context, String name, String gender){
        TextView tname = view.findViewById(R.id.name_txt);
        TextView tgender = view.findViewById(R.id.gender_txt);

        tname.setText(name);
        tgender.setText(gender);
    }
}
