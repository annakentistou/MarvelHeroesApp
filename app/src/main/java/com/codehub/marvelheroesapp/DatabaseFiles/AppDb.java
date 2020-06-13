package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {DBusers.class},exportSchema = false, version = 1)
public abstract class AppDb extends RoomDatabase {
    private static AppDb instance;

    @NonNull
  private static AppDb getInstance(Context ctx){
      if(instance == null){
          //instance abstract class
          instance = Room.databaseBuilder(ctx.getApplicationContext(),AppDb.class, "MarvelHeroesApp.db")
                  .fallbackToDestructiveMigration()
                  .build();
      }
      return instance;
  }

  public abstract DBusersDao getDBusersDaoModel();
}
