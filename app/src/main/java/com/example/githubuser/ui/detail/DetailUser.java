package com.example.githubuser.ui.detail;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubuser.R;
import com.example.githubuser.database.Result;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.entity.UserGitSelect;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class DetailUser extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    public String UserSelect;

    private ActivityDetailUserBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.following, R.string.followers};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        UserGitSelect userGitSelect;
        if (android.os.Build.VERSION.SDK_INT >= 33) {
        userGitSelect = getIntent().getParcelableExtra(EXTRA_USER, UserGitSelect.class);
        } else {
            userGitSelect = getIntent().getParcelableExtra(EXTRA_USER);
        }
        UserSelect = userGitSelect.getUserName();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, UserSelect);
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        binding.tvUsername.setText(UserSelect);
        binding.tvnamaUser.setText(userGitSelect.getNamaUser());
        binding.tvFollowers.setText(userGitSelect.getFollowers());
        binding.tvFollowing.setText(userGitSelect.getFollowing());
        Glide.with(this.getApplicationContext())
                .load(userGitSelect.getAvatar_url())
                .apply(new RequestOptions())
                .into(binding.imgDetailUser);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Buat Favorite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}