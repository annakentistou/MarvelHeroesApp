package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "USER.DB";
    private static final String TABLE_NAME = "user";

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id integer primary key AUTOINCREMENT,email text NOT NULL,password text NOT NULL, usernamere text NOT NULL, name text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }

    public boolean insert(String id, String email, String password, String usernamere, String full_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("usernamere", usernamere);
        contentValues.put("name", full_name);
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
    public boolean emailpassword(String usernamere, String password) {
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
        return db.update("user", args, "email=" + email, null) > 0;
    }

    public List<User> getUserList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM user", null);

        while (c.moveToNext()) {
            User user = new User();
            user.setEmail(c.getString(0));
            user.setPassword(c.getString(1));
            user.setUsername(c.getString(2));
            user.setName(c.getString(3));
            list.add(user);
        }
        return list;
    }

    public User getUser(String intent) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id, email, password, usernamere, name FROM user WHERE usernamere" + "='" +intent+" ' ", null);

        User user = new User();

        c.moveToNext();
        user.setId(c.getString(0));
        user.setEmail(c.getString(1));
        user.setPassword(c.getString(2));
        user.setUsername(c.getString(3));
        user.setName(c.getString(4));
        return user;
    }

/*  public String[] getName(String usernamere){
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
  }*/

}

