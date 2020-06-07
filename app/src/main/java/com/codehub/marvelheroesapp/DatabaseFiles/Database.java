package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "Login.db";
    private static final String TABLE_NAME = "user";

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key,password text, usernamere text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public boolean insert(String email, String password, String usernamere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("usernamere", usernamere);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean chkemail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return false; //email already exists
        } else {
            return true;
        }
    }

    public boolean chkuser(String usernamere) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where usernamere=?", new String[]{usernamere});
        if (cursor.getCount() > 0){
            return false;
        } else {
            return true;
        }
    }

    //cheking the username and the password
    public Boolean emailpassword(String usernamere, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where usernamere=? and password=?", new String[]{usernamere, password});
        if (cursor.getCount() > 0) {
            return true;
        }else{
            return false;
        }
    }

    //checking the  password
    public boolean chkpassword(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where password=?", new String[]{password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateEntry(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("password", password);
        return db.update("Login.db", args, "email=" + email, null) > 0;
    }

  public String[] getName(String usernamere){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT usernamere FROM user WHERE usernamere=?", new String[]{usernamere});
        String[] data = null;
        if (cursor.moveToFirst()) {
            do {
                ArrayList userInfo = new ArrayList();
                userInfo.add(data);
            }while (cursor.moveToNext());
            }
        cursor.close();
        return data;
  }

}

