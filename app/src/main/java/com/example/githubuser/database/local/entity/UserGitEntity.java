package com.example.githubuser.database.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserGit")
public class UserGitEntity implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "bookmark")
    private boolean bookmark;

    @ColumnInfo(name = "avatar_url")
    private String avatar_url;

    @ColumnInfo(name = "followers_url")
    private String followers_url;

    @ColumnInfo(name = "following_url")
    private String following_url;

    @ColumnInfo(name = "login")
    private String username;
    @ColumnInfo(name = "name")
    private String namaUser;

    protected UserGitEntity(Parcel in) {
        id = in.readInt();
        bookmark = in.readBoolean();
        avatar_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        username = in.readString();
        namaUser = in.readString();
    }

    public static final Creator<UserGitEntity> CREATOR = new Creator<UserGitEntity>() {
        @Override
        public UserGitEntity createFromParcel(Parcel in) {
            return new UserGitEntity(in);
        }

        @Override
        public UserGitEntity[] newArray(int size) {
            return new UserGitEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeBoolean(bookmark);
        parcel.writeString(avatar_url);
        parcel.writeString(followers_url);
        parcel.writeString(following_url);
        parcel.writeString(username);
        parcel.writeString(namaUser);
    }

    public UserGitEntity(int id, boolean bookmark, String avatar_url, String followers_url, String following_url, String username, String namaUser) {
        this.id = id;
        this.bookmark = bookmark;
        this.avatar_url = avatar_url;
        this.followers_url = followers_url;
        this.following_url = following_url;
        this.username = username;
        this.namaUser = namaUser;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }
}
