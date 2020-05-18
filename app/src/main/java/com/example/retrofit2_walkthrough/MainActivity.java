package com.example.retrofit2_walkthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textview_result);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")           //this is the base URL that I told you in "Post.java" on which we apply the relative URL.
                .addConverterFactory(GsonConverterFactory.create())         // this is how we say that we want GSON to parse the response
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi.class);      // here we cannot donot do "new JsonPlaceHolderApi" coz its just interface, we use tell retrofit to declear the functions for us.

        //to execute network request
        Call<List<Post>> call =jsonPlaceHolderApi.getPosts();  // .getPosts() method can be called since retrofit al ready created the implementation for this method

        // .enqueue() is a method which will handle the background networking on a separate thread rather than on Main Thread which may lead to crashing of app
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+ response.code());
                    return;
                }
                List<Post> posts=response.body();
                for (Post post :posts){
                    String content="";
                    content+="ID: "+ post.getId()+"\n";
                    content+="User ID: " +post.getUserId()+"\n";
                    content+="Title: "+post.getTitle()+"\n";
                    content+="Text: "+post.getText()+"\n\n";

                    textView.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
}
