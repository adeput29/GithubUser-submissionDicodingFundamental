package com.example.githubuser.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubuser.database.GitUserRepository;
import com.example.githubuser.di.Injection;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private static volatile MainViewModelFactory INSTANCE;

    private final GitUserRepository gitUserRepository;

    private MainViewModelFactory(GitUserRepository gitUserRepository) {
        this.gitUserRepository = gitUserRepository;
    }

    public static MainViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MainViewModelFactory.class) {
                INSTANCE = new MainViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainUserGitViewModel.class)) {
            return (T) new MainUserGitViewModel(gitUserRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
