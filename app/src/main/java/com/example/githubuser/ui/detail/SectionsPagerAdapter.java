package com.example.githubuser.ui.detail;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.ui.detail.UserFragment;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    private String UserSelected;
    private int userID;

    private boolean isBookmark;


    SectionsPagerAdapter(AppCompatActivity activity, String UserSelected, int userID, boolean isBookmark) {
        super(activity);
        this.UserSelected = UserSelected;
        this.userID = userID;
        this.isBookmark = isBookmark;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserFragment.USER_SELECTED,UserSelected);
        bundle.putInt(UserFragment.USER_ID, userID);
        bundle.putBoolean(UserFragment.USER_BOOLEAN, isBookmark);
        if (position == 0) {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWING);
        } else {
            bundle.putString(UserFragment.ARG_TAB, UserFragment.TAB_FOLLOWERS);
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
