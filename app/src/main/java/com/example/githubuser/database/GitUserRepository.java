package com.example.githubuser.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.room.UserGitDao;
import com.example.githubuser.database.remote.response.AllUserGitResponse;
import com.example.githubuser.database.remote.response.GitUserResponse;
import com.example.githubuser.database.remote.retrofit.ApiService;
import com.example.githubuser.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitUserRepository {
    private ArrayList<AllUserGitResponse.GithubUser> listUser;
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

        //Call<GitUserResponse> client = apiService.getUserGit(BuildConfig.API_KEY);
        Call<GitUserResponse> client = apiService.getUserGit("adeput29");
        client.enqueue(new Callback<GitUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitUserResponse> call, @NonNull Response<GitUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listUser = response.body().getGithubUsers();
                        //List<ArticlesItem> articles = response.body().getArticles();
                        ArrayList<UserGitEntity> newlist = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for (GitUserResponse gitUserResponse : listUser) {
                                Boolean isBookmarked = userGitDao.isUserBookmark(gitUserResponse.getId());
                                UserGitEntity list = new UserGitEntity(
                                        gitUserResponse.getId(),
                                        isBookmarked,
                                        gitUserResponse.getAvatarUrl(),
                                        gitUserResponse.getFollowersUrl(),
                                        gitUserResponse.getFollowingUrl(),
                                        gitUserResponse.getLogin(),
                                        gitUserResponse.getName()
                                );
                                newlist.add(list);
                            }
                            userGitDao.deleteAll();
                            userGitDao.insertUserGit(newlist);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitUserResponse> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        LiveData<List<UserGitEntity>> localData = userGitDao.getUserGit();
        if (localData != null) {
            result.addSource(localData, newData -> result.setValue(new Result.Success<>(newData)));
        }

        return result;
    }

    public LiveData<List<UserGitEntity>> getBookmark() {
        return userGitDao.getBookmark();
    }

    public void setBookmark(UserGitEntity userGitEntity, boolean bookmarkState) {
        appExecutors.diskIO().execute(() -> {
            userGitEntity.setBookmark(bookmarkState);
            userGitDao.updateUserGit(userGitEntity);
        });
    }



}
