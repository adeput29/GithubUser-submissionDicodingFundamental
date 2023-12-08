package com.example.githubuser.database.remote.retrofit;
import com.example.githubuser.database.remote.response.GitUserResponse;

import retrofit2.Call;
import retrofit2.http.*;

interface ApiService {
    @GET("detail/{id}")
    Call<GitUserResponse> getRestaurant(@Path("id") String id);
}
