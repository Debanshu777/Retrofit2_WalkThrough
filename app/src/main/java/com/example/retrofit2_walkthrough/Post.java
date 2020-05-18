package com.example.retrofit2_walkthrough;

import com.google.gson.annotations.SerializedName;
//This is the java object that we want to create from the after GSON converter

public class Post {
    private  int userId;
    private  Integer id;
    private String title;
    @SerializedName("body")
    private String text;

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

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
