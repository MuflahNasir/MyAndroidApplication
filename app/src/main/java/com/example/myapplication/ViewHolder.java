package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View view;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
        //On Press
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //On Long Press
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mClickListener.onItemLongClick(view, getAdapterPosition());

                return false;
            }
        });
    }
    public void setDetails(Context context, String title, String image){
        TextView Ititle = view.findViewById(R.id.imageText);
        ImageView Iimage = view.findViewById(R.id.image);

        Ititle.setText(title);
        Picasso.get().load(image).into(Iimage);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }

    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOneClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
