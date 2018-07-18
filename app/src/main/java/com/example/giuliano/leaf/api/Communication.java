package com.example.giuliano.leaf.api;

import com.example.giuliano.leaf.model.Authentication;
import com.example.giuliano.leaf.model.Classification;
import com.example.giuliano.leaf.model.Register;
import com.example.giuliano.leaf.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Communication {

    static final String BASE_URL = "http://192.168.43.205:3000/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Signin
    @POST("api/auth/signin")
    Call<User> signin(@Body Authentication authentication);

    // SiginUp
    @POST("api/auth/signup")
    Call<User> signup(@Body Register register);

    // Get list planos
    @GET("api/classificadors")
    Call<List<Classification>> getClassificacoes(@Header("Cookie") String cookie);

}
