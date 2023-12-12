package com.example.githubuser.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.githubuser.R;
import com.example.githubuser.database.Result;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.databinding.FragmentMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    public static final String EXTRA_SEARCH = "extra_search";
    public String search_text = "";

    public MainFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MainViewModelFactory factory = MainViewModelFactory.getInstance(getActivity());
        MainUserGitViewModel viewModel = new ViewModelProvider(this, factory).get(MainUserGitViewModel.class);

        MainUserGitAdapter userGitAdapter = new MainUserGitAdapter(userGitEntity -> {
            if (userGitEntity.getBookmark()) {
                viewModel.deleteUser(userGitEntity);
            } else {
                viewModel.saveUser(userGitEntity);
            }
        });

        viewModel.getUserGit(search_text).observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading){
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success){
                    binding.progressBar.setVisibility(View.GONE);
                    viewModel.getMainUser(true).observe(getViewLifecycleOwner(), listUserMain -> {
                        binding.progressBar.setVisibility(View.GONE);
                        userGitAdapter.submitList(listUserMain);
                    });
                } else if (result instanceof Result.Error){
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<List<UserGitEntity>>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setAdapter(userGitAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        search_text = getArguments().getString(EXTRA_SEARCH);
        binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}