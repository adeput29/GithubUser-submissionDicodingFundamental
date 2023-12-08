package com.example.githubuser.database.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.githubuser.database.local.entity.UserGitEntity;

import java.util.List;

public interface UserGitDao {
    @Query("SELECT * FROM UserGit ORDER BY id DESC")
    LiveData<List<UserGitEntity>> getUserGit();

    @Query("SELECT * FROM UserGit where bookmark = 1")
    LiveData<List<UserGitEntity>> getBookmark();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserGit(List<UserGitEntity> UserGit);

    @Update
    void updateUserGit(UserGitEntity UserGit);

    @Query("DELETE FROM UserGit WHERE bookmark = 0")
    void deleteAll();

    @Query("SELECT EXISTS(SELECT * FROM UserGit WHERE id = :id AND bookmark = 1)")
    Boolean isUserBookmark(String id);
}
