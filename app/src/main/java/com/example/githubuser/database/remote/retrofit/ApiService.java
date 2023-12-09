package com.example.githubuser.database.remote.retrofit;
import com.example.githubuser.database.remote.response.GitUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //@Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    //@GET("search/users?q=")
    @GET("users/{login}")
    @Headers({"Authorization: token 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"})
    Call<GitUserResponse> getUserGit(@Path("login") String login);

    @GET("users/{login}")
    @Headers({"Authorization: token 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"})
    Call<GitUserResponse> getUserGitDetail(@Path("login") String login);

    @GET("users/{login}/following")
    @Headers({"Authorization: token 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"})
    Call<GitUserResponse> getUserGitFollowing(@Path("login") String login);

    @GET("users/{login}/followers")
    @Headers({"Authorization: token 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"})
    Call<GitUserResponse> getUserGitFollowers(@Path("login") String login);
}
