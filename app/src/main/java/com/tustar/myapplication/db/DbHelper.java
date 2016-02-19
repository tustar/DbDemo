package com.tustar.myapplication.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tustar on 15-12-31.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test";
    private static final int DB_VERSION = 1;
    private static final String DROP_USER_SQL = "drop user if exists user";
    private static final String CREATE_USER_SQL = "create table if not exists user(_ID integer primary key, name varchar(100), age integer);";

    public DbHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_SQL);
        db.execSQL(CREATE_USER_SQL);
    }
}
