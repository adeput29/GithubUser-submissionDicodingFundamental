package com.example.githubuser.di;

import android.content.Context;

import com.example.githubuser.database.GitUserRepository;
import com.example.githubuser.database.local.room.UserGitDao;
import com.example.githubuser.database.local.room.UserGitDatabase;
import com.example.githubuser.database.remote.retrofit.ApiConfig;
import com.example.githubuser.database.remote.retrofit.ApiService;
import com.example.githubuser.utils.AppExecutors;

public class Injection {
    public static GitUserRepository provideRepository(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        UserGitDatabase database = UserGitDatabase.getInstance(context);
        UserGitDao dao = database.userGitDao();
        AppExecutors appExecutors = new AppExecutors();
        return GitUserRepository.getInstance(apiService, dao, appExecutors);
    }
}