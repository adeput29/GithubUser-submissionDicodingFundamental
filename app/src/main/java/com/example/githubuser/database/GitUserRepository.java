package com.example.githubuser.database;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.room.UserGitDao;
import com.example.githubuser.database.remote.response.GitItems;
import com.example.githubuser.database.remote.response.GitResponse;
import com.example.githubuser.database.remote.retrofit.ApiService;
import com.example.githubuser.utils.AppExecutors;

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

    public LiveData<Result<List<UserGitEntity>>> getUserGit(String query) {
        result.setValue(new Result.Loading<>());
        Call<GitResponse> client = apiService.getUserGit(query);
        client.enqueue(new Callback<GitResponse>() {
            @Override
            public void onResponse(@NonNull Call<GitResponse> call, @NonNull Response<GitResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<GitItems> itemUser = response.body().getItems();
                        ArrayList<UserGitEntity> newlist = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for (GitItems gitItems : itemUser) {
                                Boolean isBookmarked = userGitDao.isUserBookmark(gitItems.getId());
                                UserGitEntity list = new UserGitEntity(
                                        gitItems.getId(),
                                        isBookmarked,
                                        gitItems.getAvatarUrl(),
                                        gitItems.getFollowersUrl(),
                                        gitItems.getFollowingUrl(),
                                        gitItems.getLogin(),
                                        gitItems.getLogin()
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
            public void onFailure(@NonNull Call<GitResponse> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        LiveData<List<UserGitEntity>> localData = userGitDao.getUserGit();
        if (localData != null) {
            result.addSource(localData, newData -> result.setValue(new Result.Success<>(newData)));
        }

        return result;
    }

    public LiveData<Result<List<UserGitEntity>>> getFollowers(String login) {
        result.setValue(new Result.Loading<>());
        Call<List<GitItems>> client = apiService.getUserGitFollowers(login);
        client.enqueue(new Callback<List<GitItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitItems>> call, @NonNull Response<List<GitItems>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //List<GitItems> itemUser = response.body().getItems();
                        ArrayList<UserGitEntity> newlist = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for(int i = 0; i < response.body().size(); i++) {
                                GitItems gitItems = (GitItems) response.body().get(i);

                                Boolean isBookmarked = userGitDao.isUserBookmark(gitItems.getId());
                                UserGitEntity list = new UserGitEntity(
                                        gitItems.getId(),
                                        isBookmarked,
                                        gitItems.getAvatarUrl(),
                                        login,
                                        gitItems.getFollowingUrl(),
                                        gitItems.getLogin(),
                                        gitItems.getLogin()
                                );

                                newlist.add(list);
                                //userGitDao.deleteAll();
                                userGitDao.insertUserGit(newlist);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GitItems>> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        LiveData<List<UserGitEntity>> localData = userGitDao.getUserGit();
        if (localData != null) {
            result.addSource(localData, newData -> result.setValue(new Result.Success<>(newData)));
        }

        return result;
    }

    public LiveData<Result<List<UserGitEntity>>> getFollowing(String login) {
        result.setValue(new Result.Loading<>());
        Call<List<GitItems>> client = apiService.getUserGitFollowing(login);
        client.enqueue(new Callback<List<GitItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitItems>> call, @NonNull Response<List<GitItems>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //List<GitItems> itemUser = response.body().getItems();
                        ArrayList<UserGitEntity> newlist = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for(int i = 0; i < response.body().size(); i++) {
                                GitItems gitItems = (GitItems) response.body().get(i);

                                Boolean isBookmarked = userGitDao.isUserBookmark(gitItems.getId());
                                UserGitEntity list = new UserGitEntity(
                                        gitItems.getId(),
                                        isBookmarked,
                                        gitItems.getAvatarUrl(),
                                        gitItems.getFollowersUrl(),
                                        login,
                                        gitItems.getLogin(),
                                        gitItems.getLogin()
                                );

                                newlist.add(list);
                                //userGitDao.deleteAll();
                                userGitDao.insertUserGit(newlist);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GitItems>> call, @NonNull Throwable t) {
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

    public LiveData<List<UserGitEntity>> getFollowersOffline(String user) {
        return userGitDao.SelectFollowers(user);
    }
    public LiveData<List<UserGitEntity>> getFollowingOffline(String user) {
        return userGitDao.SelectFollowing(user);
    }

    public void setBookmark(UserGitEntity userGitEntity, boolean bookmarkState) {
        appExecutors.diskIO().execute(() -> {
            userGitEntity.setBookmark(bookmarkState);
            userGitDao.updateUserGit(userGitEntity);
        });
    }

    public void addBookmark(int id){
        appExecutors.diskIO().execute(() ->{
            userGitDao.addBookMark(id);
        });
    }

    public void removeBookmark(int id){
        appExecutors.diskIO().execute(() ->{
            userGitDao.removeBookMark(id);
        });
    }

    public void isBookmark(int id){
        appExecutors.diskIO().execute(() ->{
            userGitDao.isUserBookmark(id);
        });
    }
}
