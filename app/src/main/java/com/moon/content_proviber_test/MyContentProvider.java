package com.moon.content_proviber_test;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MyContentProvider extends ContentProvider {

    SQLiteDatabase db;
    static final String TABLE_NAME = "People";
    // URI 변수
    public static final String CP_AUTHORITY = "com.moon.database";
    public static final Uri CONTENT_URI =
            Uri.parse("content://"+ CP_AUTHORITY + "/People");

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "Delete Data");

        String nameArr[] = { s };

        // 리턴값: 삭제한 수
        int n = db.delete(TABLE_NAME, "NAME = ?", nameArr);

        return n;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "Insert Data");

        // 리턴값: 생성된 데이터의 id
        long n = db.insert(TABLE_NAME, null, contentValues);

        if (n > 0)
        {
            Uri notifyUri = ContentUris.withAppendedId(CONTENT_URI, n);
            getContext().getContentResolver().notifyChange(notifyUri, null);
            return notifyUri;
        }

        return null;
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), "DB", null,1);
        db = databaseHelper.getWritableDatabase(); //쓰기 가능한 SQLiteDatabase 인스턴스 구함
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "Update Data");;

        String nameArr[] = { s };

        // 리턴값: 업데이트한 수
        int n = db.update(TABLE_NAME, contentValues, "NAME = ?", nameArr);

        return n;
    }
}