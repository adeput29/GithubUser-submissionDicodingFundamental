package com.example.githubuser.ui.listFav;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubuser.database.GitUserRepository;
import com.example.githubuser.di.Injection;
import com.example.githubuser.ui.main.MainUserGitViewModel;
import com.example.githubuser.ui.main.MainViewModelFactory;

public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private static volatile ListViewModelFactory INSTANCE;

    private final GitUserRepository gitUserRepository;

    private ListViewModelFactory(GitUserRepository gitUserRepository) {
        this.gitUserRepository = gitUserRepository;
    }

    public static ListViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ListViewModelFactory.class) {
                INSTANCE = new ListViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(gitUserRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
