package com.codehub.marvelheroesapp.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
        return value != -1;
    }

    public Cursor read_all_data(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    public boolean check_email(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userTable where email =?", new String[]{email});
        return cursor.getCount() <= 0; //email already exists
    }

    public boolean check_user(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from userTable where username=?", new String[]{username});
        return cursor.getCount() <= 0;
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
}
