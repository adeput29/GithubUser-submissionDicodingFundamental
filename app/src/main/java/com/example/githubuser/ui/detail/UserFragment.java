package com.example.githubuser.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.githubuser.database.Result;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.databinding.FragmentUserBinding;

import java.util.List;


public class UserFragment extends Fragment {

    public static final String ARG_TAB = "tab_name";
    public static final String TAB_FOLLOWERS = "FOLLOWERS";
    public static final String TAB_FOLLOWING = "FOLLOWING";

    public static final String USER_SELECTED = "user_selected";

    private String UserSelected;

    private FragmentUserBinding binding;
    private String tabName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_TAB);
            UserSelected = getArguments().getString(USER_SELECTED);
        }

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        UserGitViewModel viewModel = new ViewModelProvider(this, factory).get(UserGitViewModel.class);

        UserGitAdapter userGitAdapter = new UserGitAdapter(userGitEntity -> {
            if (userGitEntity.getBookmark()) {
                viewModel.deleteUser(userGitEntity);
            } else {
                viewModel.saveUser(userGitEntity);
            }
        });

        if (tabName.equals(TAB_FOLLOWING)) {
            Toast.makeText(getContext(),"TAB FOLLOWING", Toast.LENGTH_LONG).show();
            viewModel.getUserFollowing(UserSelected).observe(getViewLifecycleOwner(), result -> {
                if (result != null) {
                    if (result instanceof Result.Loading){
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else if (result instanceof Result.Success){
                        binding.progressBar.setVisibility(View.GONE);
                        List<UserGitEntity> userData = ((Result.Success<List<UserGitEntity>>) result).getData();
                        userGitAdapter.submitList(userData);
                    } else if (result instanceof Result.Error){
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<List<UserGitEntity>>) result).getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (tabName.equals(TAB_FOLLOWERS)){
            Toast.makeText(getContext(),"TAB FOLLOWERS", Toast.LENGTH_LONG).show();
            viewModel.getUserFollowers(UserSelected).observe(getViewLifecycleOwner(), result -> {
                if (result != null) {
                    if (result instanceof Result.Loading){
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else if (result instanceof Result.Success){
                        binding.progressBar.setVisibility(View.GONE);
                        List<UserGitEntity> userData = ((Result.Success<List<UserGitEntity>>) result).getData();
                        userGitAdapter.submitList(userData);
                    } else if (result instanceof Result.Error){
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<List<UserGitEntity>>) result).getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setAdapter(userGitAdapter);

        setMode(tabName);


    }

    public void setMode(String tab) {
        switch (tab) {
            case TAB_FOLLOWING:
                //Toast.makeText(getContext(),"TAB FOLLOWING", Toast.LENGTH_LONG).show();

                break;
            case TAB_FOLLOWERS:
                //Toast.makeText(getContext(),"TAB FOLLOWERS", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}