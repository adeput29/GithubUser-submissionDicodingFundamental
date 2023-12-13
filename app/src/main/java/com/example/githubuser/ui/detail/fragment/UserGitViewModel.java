package com.example.githubuser.ui.detail.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuser.database.GitUserRepository;
import com.example.githubuser.database.Result;
import com.example.githubuser.database.local.entity.UserGitEntity;

import java.util.List;

public class UserGitViewModel extends ViewModel {
    private final GitUserRepository gitUserRepository;
    public UserGitViewModel(GitUserRepository gitUserRepository){
        this.gitUserRepository = gitUserRepository;
    }

    public LiveData<Result<List<UserGitEntity>>> getUserFollowing(String a){
        return gitUserRepository.getFollowing(a);
    }

    public LiveData<Result<List<UserGitEntity>>> getUserFollowers(String a){
        return gitUserRepository.getFollowers(a);
    }

    public LiveData<List<UserGitEntity>> getBookmark(){
        return gitUserRepository.getBookmark();
    }

    public LiveData<List<UserGitEntity>> getFollowersOffline(String user){
        return gitUserRepository.getFollowersOffline(user);
    }

    public LiveData<List<UserGitEntity>> getFollowingOffline(String user){
        return gitUserRepository.getFollowingOffline(user);
    }

    public LiveData<List<UserGitEntity>> selectedUser(String user){
        return gitUserRepository.getSelectedUser(user);
    }

    public void saveUser(UserGitEntity userGitEntity){
        gitUserRepository.setBookmark(userGitEntity, true);
    }

    public void deleteUser(UserGitEntity userGitEntity){
        gitUserRepository.setBookmark(userGitEntity, false);
    }

    public void saveBookmark(int id){
        gitUserRepository.addBookmark(id);
    }

    public void deleteBookmark(int id){
        gitUserRepository.removeBookmark(id);
    }

    public void isBookmark(int id){
        gitUserRepository.isBookmark(id);
    }
}
