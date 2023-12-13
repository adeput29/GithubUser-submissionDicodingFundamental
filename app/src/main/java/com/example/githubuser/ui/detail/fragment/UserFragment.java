package com.example.githubuser.ui.detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.example.githubuser.databinding.FragmentUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class UserFragment extends Fragment {
    public static final String ARG_TAB = "tab_name";
    public static final String TAB_FOLLOWERS = "FOLLOWERS";
    public static final String TAB_FOLLOWING = "FOLLOWING";

    public static final String USER_SELECTED = "user_selected";
    public static final String USER_ID = "user_id";
    public static final String USER_BOOLEAN = "user_boolean";
    private String UserSelected;
    private int userID;

    private boolean isBookmark;

    private FragmentUserBinding binding;
    private String tabName;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = binding.fab;

        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_TAB);
            UserSelected = getArguments().getString(USER_SELECTED);
            userID = getArguments().getInt(USER_ID);
            isBookmark = false;
            isBookmark = getArguments().getBoolean(USER_BOOLEAN);
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


        if (isBookmark) {
            fab.setImageDrawable(ContextCompat.getDrawable(fab.getContext(), R.drawable.ic_favorite_white_true));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(fab.getContext(), R.drawable.ic_favorite_white_false));
        }

        if (tabName.equals(TAB_FOLLOWING)) {
            viewModel.getUserFollowing(UserSelected).observe(getViewLifecycleOwner(), result -> {
                if (result != null) {
                    if (result instanceof Result.Loading){
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else if (result instanceof Result.Success){
                        viewModel.getFollowingOffline(UserSelected).observe(getViewLifecycleOwner(), listFollowing -> {
                            binding.progressBar.setVisibility(View.GONE);
                            userGitAdapter.submitList(listFollowing);
                        });
                    } else if (result instanceof Result.Error){
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<List<UserGitEntity>>) result).getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (tabName.equals(TAB_FOLLOWERS)){
            viewModel.getUserFollowers(UserSelected).observe(getViewLifecycleOwner(), result -> {
                if (result != null) {
                    if (result instanceof Result.Loading){
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else if (result instanceof Result.Success){
                        viewModel.getFollowersOffline(UserSelected).observe(getViewLifecycleOwner(), listFollowers -> {
                            binding.progressBar.setVisibility(View.GONE);
                            userGitAdapter.submitList(listFollowers);
                        });

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBookmark) {
                    int Catch = userID;
                    viewModel.deleteBookmark(Catch);
                    fab.setImageDrawable(ContextCompat.getDrawable(fab.getContext(), R.drawable.ic_favorite_white_false));
                    Snackbar.make(view, "Delete Favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    isBookmark = false;
                } else {
                    int Catch = userID;
                    viewModel.saveBookmark(Catch);
                    fab.setImageDrawable(ContextCompat.getDrawable(fab.getContext(), R.drawable.ic_favorite_white_true));
                    Snackbar.make(view, "Save Favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    isBookmark = true;
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}