package com.example.myapplication;

public class Upload {
    private String title;
    private  String image;

    public Upload(){}

    public Upload (String name, String imageUri){
        if (name.trim().equals("")){
            name = "No Name";
        }
        title = name;
        image = imageUri;
    }

    public String getmTitle() {
        return title;
    }

    public void setmTitle(String title) {
        this.title = title;
    }

    public String getmImage() {
        return image;
    }

    public void setmImage(String image) {
        this.image = image;
    }
}
