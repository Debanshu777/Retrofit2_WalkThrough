package com.example.retrofit2_walkthrough;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")                     //This annotation will fill with required code to fetch
                                      // "posts" is the relative URL to the to the JSONPlaceHolder("https://jsonplaceholder.typicode.com/")
    Call<List<Post>> getPosts();      //We want to get back from this call a list of JSON array of Post JSON objects
                                      // The "Call" object encapsulate a single Request and respond which will be used to execute @GET request


}
