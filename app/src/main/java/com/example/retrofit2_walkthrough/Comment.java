package com.example.retrofit2_walkthrough;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private  int postId;
    private  int id;
    private String name;
    private String email;
    @SerializedName("body")
    private String text;


    public String getEmail() {
        return email;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
