package com.codehub.marvelheroesapp.DatabaseFiles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbUsers")
public class DBusers {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    private int id;
    @ColumnInfo(name = "full_name")
    private String full_name;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "image")
    private byte[] image;

    public DBusers(int id, String full_name, String username, String password, String email, byte[] image) {
        this.id = id;
        this.full_name = full_name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "DBusers{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
