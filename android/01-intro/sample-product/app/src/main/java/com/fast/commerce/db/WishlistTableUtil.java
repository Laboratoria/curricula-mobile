package com.fast.commerce.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fast.commerce.wishlist.WishListItem;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.name;

public class WishlistTableUtil {
    private static final String TABLE_WISHLIST = "wishlist";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_WISHLIST_TEXT = "item_text";
    private static final String COLUMN_WISHLIST_TIME_MODIFIED = "last_modified_time";

    // Database creation sql statement
    // create table wishlist( id integer primary key autoincrement, item_text text not null, creation_time INTEGER);
    private static final String DATABASE_CREATE = "create table " +
            TABLE_WISHLIST + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_WISHLIST_TEXT + " text not null, " +
            COLUMN_WISHLIST_TIME_MODIFIED + " INTEGER " +
            ");";

    private static final String[] INDEXES = {
            COLUMN_WISHLIST_TIME_MODIFIED,
    };

    public static void onCreate(SQLiteDatabase db) {
//        DbUtil.createTableIfNotExists(db, TABLE_WISHLIST, );
        db.execSQL(DATABASE_CREATE);
        DbUtil.createIndexIfNotExists(db, TABLE_WISHLIST, INDEXES);
    }

    public static void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static long getCount(SQLiteDatabase db) {
        return DbUtil.getRowCount(db, TABLE_WISHLIST);
    }

    private static WishListItem getWishListItem(Cursor cursor) {
        long id = DbUtil.getLongFromCursor(cursor, COLUMN_ID);
        String text = DbUtil.getStringFromCursor(cursor, COLUMN_WISHLIST_TEXT);
        long time = DbUtil.getLongFromCursor(cursor, COLUMN_WISHLIST_TIME_MODIFIED);
        return new WishListItem(id, text, time);
    }

    public static ArrayList<WishListItem> getWishlistItems(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_WISHLIST,
                null, /* ALL COLUMNS */
                null, /* selection */
                null, /* selectionArgs */
                null, /* groub by */
                null, /* having */
                COLUMN_WISHLIST_TIME_MODIFIED + " DESC" /* order by*/
        );
        ArrayList<WishListItem> list = new ArrayList<>(cursor.getCount());
        boolean valid = cursor.moveToFirst();
        while (valid) {
            list.add(getWishListItem(cursor));
            valid = cursor.moveToNext();
        }
        return list;
    }

    public static long addWishlistItem(SQLiteDatabase db, String text) {
        WishListItem item = new WishListItem();
        item.setText(text);
        return addWishlistItem(db, item);
    }

    public static long addWishlistItem(SQLiteDatabase db, WishListItem item) {
        long timestamp = System.currentTimeMillis();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WISHLIST_TEXT, item.getText());
        cv.put(COLUMN_WISHLIST_TIME_MODIFIED, timestamp);
        if (item.getId() >= 0) {
            cv.put(COLUMN_ID, item.getId());
        }

        long val = db.insert(TABLE_WISHLIST, null, cv);
        if (val < 0) {
            Log.e("FAST", "Unable to add wishlist");
        }
        return val;
    }

    public static void deleteWishlistItem(SQLiteDatabase db, WishListItem item) {
        int deleted = db.delete(TABLE_WISHLIST, COLUMN_ID + "=" + item.getId(), null);
        if (deleted != 1) {
            Log.e("FAST", "Unable to delete item in wishlist. Value of deleted=" + deleted);
        }
    }

    private WishlistTableUtil() {}

}
