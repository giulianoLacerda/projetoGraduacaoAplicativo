package com.example.giuliano.leaf.api;

import com.example.giuliano.leaf.model.Authentication;
import com.example.giuliano.leaf.model.Classification;
import com.example.giuliano.leaf.model.Register;
import com.example.giuliano.leaf.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {
    //@GET("/search/users?q=language:java+location:lagos")
    //Call<ItemResponse> getItems();z

    // Signin
    @POST("api/auth/signin")
    Call<User> signin(@Body Authentication authentication);

    // SiginUp
    @POST("api/auth/signup")
    Call<User> signup(@Body Register register);

    // Get list planos
    @GET("api/classificadors")
    Call<List<Classification>> getClassificacoes(@Header("Cookie") String cookie);

    @Multipart
    @POST("api/classificadors")
    Call<Classification> postImage(@Part("description") RequestBody description,
                                   @Part MultipartBody.Part file,
                                   @Header("Cookie") String cookie);
}
