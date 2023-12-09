package com.example.githubuser.ui.detail;

import android.os.Bundle;

import com.example.githubuser.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailUser extends AppCompatActivity {

    private ActivityDetailUserBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.home, R.string.bookmark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

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