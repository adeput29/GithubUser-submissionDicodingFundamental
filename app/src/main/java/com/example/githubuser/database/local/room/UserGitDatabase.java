package com.example.githubuser.database.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.githubuser.database.local.entity.UserGitEntity;

@Database(entities = {UserGitEntity.class},
        version = 1,
        exportSchema = false)
public abstract class UserGitDatabase extends RoomDatabase {
    public abstract UserGitDao userGitDao();

    private static volatile UserGitDatabase INSTANCE;

    public static UserGitDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (UserGitDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserGitDatabase.class, "UserGit.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}
