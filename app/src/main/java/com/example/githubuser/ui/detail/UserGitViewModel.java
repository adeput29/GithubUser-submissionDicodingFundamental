package com.example.githubuser.ui.detail;

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

    public LiveData<List<UserGitEntity>> getBookmark(){
        return gitUserRepository.getBookmark();
    }

    public void saveUser(UserGitEntity userGitEntity){
        gitUserRepository.setBookmark(userGitEntity, true);
    }

    public void deleteUser(UserGitEntity userGitEntity){
        gitUserRepository.setBookmark(userGitEntity, false);
    }
}
