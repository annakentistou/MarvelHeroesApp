package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
        public Database(Context context) {
            super (context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table user(email text primary key,password text, usernamere text)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("drop table if exists user");
        }

        public boolean insert (String email, String password, String usernamere){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues =  new ContentValues();
            contentValues.put("email",email);
            contentValues.put("password",password);
            contentValues.put("usernamere",usernamere);
            long ins = db.insert("user", null, contentValues);
            if(ins==-1) return false;
            else return true;
        }
        public Boolean chkemail (String email){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from user where email=?",new String[]{email});
            if (cursor.getCount()>0) return false;
            else return true;
        }

        public Boolean chkuser (String usernamere){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from user where usernamere=?",new String[]{usernamere});
            if (cursor.getCount()>0) return false;
            else return true;
        }
        //cheking the username and the password
        public Boolean emailpassword(String usernamere, String password){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from user where usernamere=? and password=?",new String[]{usernamere,password});
            if (cursor.getCount()>0) return true;
            else return false;
        }

        //cheking the  password
        public Boolean chkpassword(String password){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from user where password=?",new String[]{password});
            if (cursor.getCount()>0) return true;
            else return false;
        }

    public boolean updateEntry(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("password", password);
        return db.update("Login.db", args, "email=" + email, null) > 0;
    }






}

