package com.tustar.myapplication.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tustar.myapplication.utils.SqliteUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 15-12-31.
 */
public class BaseDao {

    private static final String TAG = BaseDao.class.getSimpleName();

    public BaseDao() {

    }

    public <T> List<T> getAll(Context context, Class<T> clazz) {
        String table = clazz.getSimpleName().toLowerCase();
        SqliteUtils mUtils = SqliteUtils.getInstance(context.getApplicationContext());
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mUtils.getDb();
            cursor = db.query(table, null, null, null, null, null, null);
            Log.d(TAG, "getAll :: count = " + cursor.getCount());
            if (cursor != null) {
                List<T> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    list.add(rowToInstance(cursor, clazz));
                }
                return list;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }

            if (null != db) {
                db.close();
            }
        }

        return null;
    }

    private <T> T rowToInstance(Cursor cursor, Class<T> clazz) throws InstantiationException,
            IllegalAccessException, NoSuchFieldException {
        T instance = clazz.newInstance();
        String[] names = cursor.getColumnNames();
        for (String name : names) {
            if ("_ID".endsWith(name)) {
                continue;
            }
            setPropertyValue(cursor, name, instance, clazz);
        }

        return instance;
    }

    private <T> T setPropertyValue(Cursor cursor, String name, T instance, Class<T> clazz)
            throws  NoSuchFieldException, IllegalAccessException {
        Log.d(TAG, "setPropertyValue :: name = " + name);
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        String type = field.getType().toString();//得到此属性的类型
        Log.d(TAG, "setPropertyValue :: type = " + type);
        if (type.endsWith("String")) {
            String value = cursor.getString(cursor.getColumnIndex(name));
            Log.d(TAG, "setPropertyValue :: value = " + value);
            field.set(instance, value);
        } else if (type.endsWith("int") || type.endsWith("Integer")) {
            int value = cursor.getInt(cursor.getColumnIndex(name));
            Log.d(TAG, "setPropertyValue :: value = " + value);
            field.setInt(instance, value);
        } else {
            // TODO:
        }

        return instance;
    }
}
