package com.example.retrofit2_walkthrough;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    //@Query() is when we get the URL in form of query-> "/posts?userId=1"(alternative of what is done for the getcomments())
    //for example,
    // Call<List<Post>> getPosts(@Query("userId")int userid);



    //for adding multiple Query parameter see the docs of JSONPlaceHolder for the queries
    // it will like "/posts?userId=1&_sort=id&_order=desc" (parameters are passed)
//    for example,
//    @GET("posts")
//    Call<List<Post>> getPosts(
//            @Query("userId")Integer userid,    //this will allow us to make it null-able
//            @Query("_sort") String sort,
//            @Query("_order") String order
//    );
    //we an also pass a list of userid by "@Query("userId") Interger[] userId" then you have to change the call too




    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String,String> parameters);//if we don't want to specify which argument to use unless called then


    // adding {id} gives us more flexibility
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id")int postid);

    //@URL put the whole url
    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title")String title,
            @Field("body")String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);



   //Adding Header to @Put
   @Headers({"Static_Header_1: 123","Static_Header_2: 456"})
    @PUT("posts/{id}")
    Call<Post> putPost(
            @Header("Dyanamic_header_1:") String header, //add argument at putPost() in MainActivity
            @Path("id")int id,
            @Body Post post);
    // we can either put multiple header using HeaderMap
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id")int id,@Body Post post);




    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id")int id);
}
