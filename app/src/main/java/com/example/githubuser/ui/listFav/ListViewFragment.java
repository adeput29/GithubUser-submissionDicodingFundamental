package com.example.githubuser.ui.listFav;

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
import com.example.githubuser.databinding.FragmentListViewBinding;
import com.example.githubuser.databinding.FragmentMainBinding;
import com.example.githubuser.ui.main.MainUserGitAdapter;
import com.example.githubuser.ui.main.MainUserGitViewModel;
import com.example.githubuser.ui.main.MainViewModelFactory;

import java.util.List;

public class ListViewFragment extends Fragment {
    FragmentListViewBinding binding;
    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListViewModelFactory factory = ListViewModelFactory.getInstance(getActivity());
        ListViewModel viewModel = new ViewModelProvider(this, factory).get(ListViewModel.class);
        ListViewAdapter listViewAdapter = new ListViewAdapter(userGitEntity -> {
            if (userGitEntity.getBookmark()) {
                viewModel.deleteUser(userGitEntity);
            } else {
                viewModel.saveUser(userGitEntity);
            }
        });

        viewModel.getBookmark().observe(getViewLifecycleOwner(), listFav -> {
            binding.progressBar.setVisibility(View.GONE);
            listViewAdapter.submitList(listFav);
        });
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setAdapter(listViewAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}