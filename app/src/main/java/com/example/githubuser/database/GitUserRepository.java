package com.example.githubuser.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.room.UserGitDao;
import com.example.githubuser.database.local.room.UserGitDatabase;
import com.example.githubuser.database.remote.response.GitUserResponse;
import com.example.githubuser.database.remote.retrofit.ApiService;
import com.example.githubuser.utils.AppExecutors;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitUserRepository {

    private volatile static GitUserRepository INSTANCE = null;
    private final ApiService apiService;

    private final UserGitDao userGitDao;

    private final AppExecutors appExecutors;

    private final MediatorLiveData<Result<List<UserGitEntity>>> result = new MediatorLiveData<>();

    private GitUserRepository(@NonNull ApiService apiService, @NonNull UserGitDao userGitDao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.userGitDao = userGitDao;
        this.appExecutors = appExecutors;
    }

    public static GitUserRepository getInstance(ApiService apiService, UserGitDao userGitDao, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (GitUserRepository.class) {
                INSTANCE = new GitUserRepository(apiService, userGitDao, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<Result<List<UserGitEntity>>> getUserGit() {
        result.setValue(new Result.Loading<>());

        Call<GitUserResponse> client = apiService.getUserGit(BuildConfig.API_KEY);
        client.enqueue(new Callback<GitUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitUserResponse> call, @NonNull Response<GitUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //List<ArticlesItem> articles = response.body().getArticles();

                        ArrayList<UserGitEntity> newsList = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for (ArticlesItem article : articles) {
                                Boolean isBookmarked = userGitDao.isUserBookmark(article.getTitle());
                                UserGitEntity userGitEntity = new UserGitEntity(
                                        article.getTitle(),
                                        article.getPublishedAt(),
                                        article.getUrlToImage(),
                                        article.getUrl(),
                                        isBookmarked
                                );
                                newsList.add(userGitEntity);
                            }
                            userGitDao.deleteAll();
                            userGitDao.insertUserGit(newsList);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitUserResponse> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        LiveData<List<UserGitEntity>> localData = userGitDao.getUserGit;
        if (localData != null) {
            result.addSource(localData, newData -> result.setValue(new Result.Success<>(newData)));
        }

        return result;
    }

    public void setNewsBookmark(UserGitEntity userGitEntity, boolean bookmarkState) {
        appExecutors.diskIO().execute(() -> {
            userGitEntity.setBookmark(bookmarkState);
            userGitDao.updateUserGit(userGitEntity);
        });
    }



}
