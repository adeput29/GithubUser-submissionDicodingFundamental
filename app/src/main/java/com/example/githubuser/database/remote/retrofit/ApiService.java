package com.example.githubuser.database.remote.retrofit;
import com.example.githubuser.database.remote.response.GitUserResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("id/{id}")
    @Headers({"Authorization: token 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"})
    Call<GitUserResponse> getUserGit(@Path("id") String id);
    //Call<GitUserResponse> getUserGit(@Query("apiKey") String apiKey);
}
