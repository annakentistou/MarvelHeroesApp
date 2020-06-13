package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class NewDbUsers extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "UsersDb";
    private static String TABLE_NAME = "userTable";
    public static String KEY_ID = "id";
    public static String NAME = "name";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String EMAIL = "email";
    public static String IMAGE = "image";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " TEXT," + NAME + " TEXT," + USERNAME + " TEXT," + PASSWORD + " TEXT,"
            + EMAIL +  " TEXT primary key," + IMAGE + " TEXT)";

    public NewDbUsers(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public boolean insert(int id, String name, String username, String password, String email, String image) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, id);
        cv.put(NAME, name);
        cv.put(USERNAME, username);
        cv.put(PASSWORD, password);
        cv.put(EMAIL, email);
        cv.put(IMAGE, image);
        long value = db.insert(TABLE_NAME, null, cv);
        Log.d("Insert in DBusers", name + ", email - " + email + " - . " + cv);
        if (value == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor read_all_data(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    public boolean check_email(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userTable where email =?", new String[]{email});
        if (cursor.getCount() > 0) {
            return false; //email already exists
        } else {
            return true;
        }
    }

    public boolean check_user(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from userTable where username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from userTable where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
