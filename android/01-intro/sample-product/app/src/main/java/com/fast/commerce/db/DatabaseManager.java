package com.fast.commerce.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.annotation.concurrent.GuardedBy;

public class DatabaseManager {
    private static final String TAG = "ATDatabaseManager";

    static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "auth.account_transfer.store.db";

    @GuardedBy("DatabaseManager.class")
    private static DatabaseManager sInstance;

    private final DatabaseHelper mDatabaseHelper;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context);
        }
        return sInstance;
    }

    private DatabaseManager(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public synchronized SQLiteDatabase getDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "wishlist.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            WishlistTableUtil.onCreate(sqLiteDatabase);
            CartTableUtil.onCreate(sqLiteDatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            WishlistTableUtil.onUpgrade(sqLiteDatabase, i , i1);
            CartTableUtil.onUpgrade(sqLiteDatabase, i , i1);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            WishlistTableUtil.onDowngrade(db, oldVersion, newVersion);
            CartTableUtil.onDowngrade(db, oldVersion, newVersion);
        }
    }

}
