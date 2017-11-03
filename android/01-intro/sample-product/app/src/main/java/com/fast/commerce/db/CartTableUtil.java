package com.fast.commerce.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fast.commerce.cart.CartItem;
import com.fast.commerce.wishlist.WishListItem;

import java.util.ArrayList;

import static com.fast.commerce.db.WishlistTableUtil.addWishlistItem;

// TODO: This should sync up with the web interface when we decide to open to web.
public class CartTableUtil {
    private static final String TABLE_CART = "cart";

    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";

    // Database creation sql statement
    // create table wishlist( item_text text not null, creation_time INTEGER);
    private static final String DATABASE_CREATE = "create table " +
            TABLE_CART + "( " +
            COLUMN_ITEM_ID + " text not null, " +
            COLUMN_ITEM_QUANTITY + " INTEGER " +
            ");";

    private static final String[] INDEXES = {
            COLUMN_ITEM_ID,
    };

    public static void onCreate(SQLiteDatabase db) {
//        DbUtil.createTableIfNotExists(db, TABLE_CART, );
        db.execSQL(DATABASE_CREATE);
        DbUtil.createIndexIfNotExists(db, TABLE_CART, INDEXES);
    }

    public static void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static long getCount(SQLiteDatabase db) {
        return DbUtil.getRowCount(db, TABLE_CART);
    }

    private static CartItem getCartItem(Cursor cursor) {
        String text = DbUtil.getStringFromCursor(cursor, COLUMN_ITEM_ID);
        int quantity = DbUtil.getIntFromCursor(cursor, COLUMN_ITEM_QUANTITY);
        return new CartItem(text, quantity);
    }

    public static ArrayList<CartItem> getCartItems(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_CART,
                null, /* ALL COLUMNS */
                null, /* selection */
                null, /* selectionArgs */
                null, /* groub by */
                null, /* having */
                null /* order by*/
        );
        ArrayList<CartItem> list = new ArrayList<>(cursor.getCount());
        boolean valid = cursor.moveToFirst();
        while (valid) {
            list.add(getCartItem(cursor));
            valid = cursor.moveToNext();
        }
        return list;
    }

    public static long updateCartItem(SQLiteDatabase db, CartItem item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_ID, item.itemId);
        cv.put(COLUMN_ITEM_QUANTITY, item.quantity);
        long val = db.update(TABLE_CART, cv, COLUMN_ITEM_ID + "=" + item.itemId, null);
        if (val < 0) {
            Log.e("FAST", "Unable to add wishlist");
        }
        return val;
    }

    public static long addCartItem(SQLiteDatabase db, CartItem item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_ID, item.itemId);
        cv.put(COLUMN_ITEM_QUANTITY, item.quantity);
        long val = db.insert(TABLE_CART, null, cv);
        if (val < 0) {
            Log.e("FAST", "Unable to add wishlist");
        }
        return val;
    }

    public static void deleteCartItem(SQLiteDatabase db, String itemId) {
        int deleted = db.delete(TABLE_CART, COLUMN_ITEM_ID + "=" + itemId, null);
        if (deleted != 1) {
            Log.e("FAST", "Unable to delete item in cart. Value of deleted=" + deleted);
        }
    }

    public static void deleteAllCartItems(SQLiteDatabase db) {
        int deleted = db.delete(TABLE_CART, null, null);
        if (deleted != 1) {
            Log.e("FAST", "Unable to delete item in cart. Value of deleted=" + deleted);
        }
    }

    private CartTableUtil() {}

}
