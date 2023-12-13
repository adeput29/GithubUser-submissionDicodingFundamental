package com.example.githubuser.ui.detail.fragment;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubuser.R;
import com.example.githubuser.database.Result;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.entity.UserGitSelect;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DetailUser extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    public String UserSelect;

    public int id;

    private ArrayList<UserGitEntity> list = new ArrayList<>();

    public boolean isBookmark;



    private ActivityDetailUserBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.following, R.string.followers};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);


        UserGitSelect userGitSelect;
        if (android.os.Build.VERSION.SDK_INT >= 33) {
        userGitSelect = getIntent().getParcelableExtra(EXTRA_USER, UserGitSelect.class);
        } else {
            userGitSelect = getIntent().getParcelableExtra(EXTRA_USER);
        }
        UserSelect = userGitSelect.getUserName();
        id = userGitSelect.getId();
        isBookmark = userGitSelect.isBookmark();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, UserSelect, id, isBookmark);
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }



        //USER SELECT
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplicationContext());
        UserGitViewModel viewModel = new ViewModelProvider(this, factory).get(UserGitViewModel.class);
        UserGitAdapter userGitAdapter = new UserGitAdapter(userGitEntity -> {
            if (userGitEntity.getBookmark()) {
                viewModel.deleteUser(userGitEntity);
            } else {
                viewModel.saveUser(userGitEntity);
            }
        });
        viewModel.getSelectedUser(userGitSelect.getUserName()).observe(this, result -> {
            if (result != null) {
                if (result instanceof Result.Loading){
                } else if (result instanceof Result.Success){
                    viewModel.selectedUserOffline(userGitSelect.getUserName()).observe(this, listSelected -> {
                        userGitAdapter.submitList(listSelected);
                        //Toast.makeText(this,listSelected.get(0).getNamaUser(),Toast.LENGTH_LONG).show();
                        binding.tvnamaUser.setText(listSelected.get(0).getNamaUser());
                        binding.tvFollowers.setText(listSelected.get(0).getFollowers_url()+" Followers");
                        binding.tvFollowing.setText(listSelected.get(0).getFollowing_url()+" Following");
                    });
                } else if (result instanceof Result.Error){
                    Toast.makeText(this, "Terjadi kesalahan"+ ((Result.Error<List<UserGitEntity>>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        binding.tvUsername.setText(userGitSelect.getUserName());
        binding.tvUserID.setText("ID: "+userGitSelect.getId());
        Glide.with(this.getApplicationContext())
                .load(userGitSelect.getAvatar_url())
                .apply(new RequestOptions())
                .into(binding.imgDetailUser);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        share();
        return super.onOptionsItemSelected(item);
    }

    public void share() {
        String text = "Username Github: "+binding.tvUsername.getText().toString()+" \nUserID:"+binding.tvUserID.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share User..."));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}