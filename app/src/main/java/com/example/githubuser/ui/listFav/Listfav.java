package com.example.githubuser.ui.listFav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.githubuser.R;
import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.example.githubuser.databinding.ActivityListfavBinding;
import com.example.githubuser.ui.main.MainFragment;

public class Listfav extends AppCompatActivity {

    ActivityListfavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfav);

        binding = ActivityListfavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ListViewFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}