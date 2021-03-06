package com.example.retrofit2_walkthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private  JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textview_result);

//        By default Gson ignore the null value in case of patch
//        but can force Gson
//        Gson gson =new GsonBuilder().serializeNulls().create();
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();



        //Logging intercepter(Retrofit by default uses OKHTTP client but we will redefine it )
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest=chain.request();                   // Rather than putting header for each we can just deo it here it will be applied for all
                        Request newRequest=originalRequest.newBuilder()
                                .header("Interceptor-log_test","xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi.class);
        //getPosts();
        //getComments();
        //createPosts();
        updatePost();
        //deletePost();
    }
    private void getPosts(){

        //for calling with argument
        //if you don't want to use a particular parameter make it null
        //Call<List<Post>> call =jsonPlaceHolderApi.getPosts(4,"id","desc");


        //for calling with multiple userId array
        //Call<List<Post>> call =jsonPlaceHolderApi.getPosts(new Integer[]{2,3,4},null,"desc");


        //for @QueryMap
        Map<String,String> parameters=new HashMap<>();
        parameters.put("userId","1"); // we can't add array like the last case
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        Call<List<Post>> call =jsonPlaceHolderApi.getPosts(parameters);

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
    private void getComments(){
        //for @Path
        //Call<List<Comment>> call =jsonPlaceHolderApi.getComments(3);

        //for @Url
        Call<List<Comment>> call =jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+ response.code());
                    return;
                }
                List<Comment> comments=response.body();
                for (Comment comment :comments){
                    String content="";
                    content+="ID: "+ comment.getId()+"\n";
                    content+="Post ID: " +comment.getPostId()+"\n";
                    content+="Name: "+comment.getName()+"\n";
                    content+="Email: "+comment.getEmail()+"\n";
                    content+="Text: "+comment.getText()+"\n\n";

                    textView.append(content);

                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
    private void createPosts(){
        //for first case normal post
       // Post post=new Post(23,"New Title","New Text");


        //in case of @FromUrlEncoded
        //Call<Post> call=jsonPlaceHolderApi.createPost(23,"New Title","New Text");

        //in case of@FromUrlEncoded array
        Map<String,String> fields=new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");
        Call<Post> call=jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }
                Post postResponse =response.body();
                String content="";
                content+="Code: "+ response.code()+"\n";
                content+="ID: "+ postResponse.getId()+"\n";
                content+="User ID: " +postResponse.getUserId()+"\n";
                content+="Title: "+postResponse.getTitle()+"\n";
                content+="Text: "+postResponse.getText()+"\n\n";

                textView.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
    private void updatePost(){
        Post post=new Post(12,null,"New Text");
        Call<Post> call=jsonPlaceHolderApi.putPost("abc",5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }
                Post postResponse =response.body();
                String content="";
                content+="Code: "+ response.code()+"\n";
                content+="ID: "+ postResponse.getId()+"\n";
                content+="User ID: " +postResponse.getUserId()+"\n";
                content+="Title: "+postResponse.getTitle()+"\n";
                content+="Text: "+postResponse.getText()+"\n\n";

                textView.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
    private void deletePost(){
        Call<Void> call=jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textView.setText("Code: "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
