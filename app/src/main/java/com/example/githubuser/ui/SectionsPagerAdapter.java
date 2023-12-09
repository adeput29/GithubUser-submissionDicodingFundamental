package com.example.githubuser.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        if (position == 0) {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_USER);
        } else {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_BOOKMARK);
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
