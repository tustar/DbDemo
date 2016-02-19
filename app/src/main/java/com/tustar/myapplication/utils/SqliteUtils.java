package com.tustar.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tustar.myapplication.db.DbHelper;

/**
 * Created by tustar on 15-12-31.
 */
public class SqliteUtils {

    private static volatile SqliteUtils instance;

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private SqliteUtils(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static SqliteUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SqliteUtils.class) {
                if (instance == null) {
                    instance = new SqliteUtils(context);
                }
            }
        }
        return instance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}