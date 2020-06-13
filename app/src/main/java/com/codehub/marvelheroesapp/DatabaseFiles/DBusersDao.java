package com.codehub.marvelheroesapp.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBusersDao {

    @Query("SELECT * FROM tbUsers")
    List<DBusers> getAll();

    @Query("SELECT * FROM tbUsers LIMIT :num")
    List<DBusers> get(int num);

    @Query("SELECT * FROM tbUsers WHERE username = :username")
    List<DBusers> get(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DBusers model);

    @Delete
    void delete(DBusers model);

}
