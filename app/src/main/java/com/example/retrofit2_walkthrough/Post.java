package com.example.retrofit2_walkthrough;

import com.google.gson.annotations.SerializedName;
//This is the java object that we want to create from the after GSON converter

public class Post {
    //these are all the keys that we ant to extract from the JSON object
    private  int userId;
    private  int id;
    private String title;
    //this annotation helps to identify which key to refer if our variable name is different
    @SerializedName("body")
    private String text;
    //generated getter methods for the variable
    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
