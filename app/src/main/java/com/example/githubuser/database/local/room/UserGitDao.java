package com.example.githubuser.database.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.githubuser.database.local.entity.UserGitEntity;

import java.util.List;
@Dao
public interface UserGitDao {
    @Query("SELECT * FROM UserGit ORDER BY id DESC")
    LiveData<List<UserGitEntity>> getUserGit();

    @Query("SELECT * FROM UserGit where bookmark = 1")
    LiveData<List<UserGitEntity>> getBookmark();

    @Query("SELECT * FROM UserGit where followers_url = :user")
    LiveData<List<UserGitEntity>> SelectFollowers(String user);

    @Query("SELECT * FROM UserGit where following_url = :user")
    LiveData<List<UserGitEntity>> SelectFollowing(String user);

    @Query("SELECT * FROM UserGit where isMain = :isMain ORDER BY id DESC")
    LiveData<List<UserGitEntity>> SelectMain(boolean isMain);

    @Query("SELECT * FROM UserGit where login = :user")
    LiveData<List<UserGitEntity>> getSelectedUser(String user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserGit(List<UserGitEntity> UserGit);

    @Update
    void updateUserGit(UserGitEntity UserGit);

    @Query("DELETE FROM UserGit WHERE bookmark = 0")
    void deleteAll();

    @Query("DELETE FROM UserGit WHERE login = :string")
    void deleteUsername(String string);

    @Query("SELECT EXISTS(SELECT * FROM UserGit WHERE id = :id AND bookmark = 1)")
    Boolean isUserBookmark(int id);

    @Query("UPDATE UserGit SET bookmark = 1 WHERE id = :id")
    void addBookMark(int id);

    @Query("UPDATE UserGit SET bookmark = 0 WHERE id = :id")
    void removeBookMark(int id);
}
