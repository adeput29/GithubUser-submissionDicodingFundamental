package com.example.githubuser.ui.detail;

import android.content.Intent;
import android.net.Uri;
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
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class DetailUser extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    public String UserSelect;

    public int id;

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

        binding.tvnamaUser.setText(userGitSelect.getNamaUser());
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
        String text = "Username Github: "+binding.tvnamaUser.getText().toString()+" \nUserID:"+binding.tvUserID.getText().toString();
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