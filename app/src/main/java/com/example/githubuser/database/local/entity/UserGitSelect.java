package com.example.githubuser.database.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserGitSelect implements Parcelable {
    String NamaUser;
    String UserName;
    String Followers;
    String Following;

    String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    boolean bookmark;

    public UserGitSelect() {
    }

    public UserGitSelect(Parcel in) {
        NamaUser = in.readString();
        UserName = in.readString();
        Followers = in.readString();
        Following = in.readString();
        bookmark = in.readByte() != 0;
        avatar_url = in.readString();
    }

    public static final Creator<UserGitSelect> CREATOR = new Creator<UserGitSelect>() {
        @Override
        public UserGitSelect createFromParcel(Parcel in) {
            return new UserGitSelect(in);
        }

        @Override
        public UserGitSelect[] newArray(int size) {
            return new UserGitSelect[size];
        }
    };

    public String getNamaUser() {
        return NamaUser;
    }

    public void setNamaUser(String namaUser) {
        NamaUser = namaUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFollowers() {
        return Followers;
    }

    public void setFollowers(String followers) {
        Followers = followers;
    }

    public String getFollowing() {
        return Following;
    }

    public void setFollowing(String following) {
        Following = following;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(NamaUser);
        dest.writeString(UserName);
        dest.writeString(Followers);
        dest.writeString(Following);
        dest.writeByte((byte) (bookmark ? 1 : 0));
        dest.writeString(avatar_url);
    }
}
