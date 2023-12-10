package com.example.githubuser.ui.detail;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.githubuser.ui.detail.UserFragment;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    private String UserSelected;


    SectionsPagerAdapter(AppCompatActivity activity, String UserSelected) {
        super(activity);
        this.UserSelected = UserSelected;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserFragment.USER_SELECTED,UserSelected);
        if (position == 0) {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWING);
        } else {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWERS);
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCurrentTab(int position){
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserFragment.USER_SELECTED,UserSelected);
        if (position == 0) {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWING);
        } else {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWERS);
        }

        fragment.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
