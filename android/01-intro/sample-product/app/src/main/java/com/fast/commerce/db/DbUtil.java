package com.fast.commerce.db;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public final class DbUtil {
    private static final String TAG = DbUtil.class.getSimpleName();

    /** Create an index for a table. */
    public static void createIndexIfNotExists(
            SQLiteDatabase db, String tableName, String[] columnNames) {
        StringBuilder builder = new StringBuilder(128);
        builder.append("CREATE INDEX IF NOT EXISTS idx_");
        builder.append(tableName);
        for (int i = 0; i < columnNames.length; i++) {
            builder.append('_');
            builder.append(columnNames[i]);
        }
        builder.append(" ON ");
        builder.append(tableName);
        builder.append('(');
        for (int i = 0; i < columnNames.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(columnNames[i]);
        }
        builder.append(");");
        db.execSQL(builder.toString());
    }

    /** Create a table if it does not exist. */
    public static void createTableIfNotExists(
            SQLiteDatabase db, String tableName, String[] columns, String[] columnTypes) {
        createTableIfNotExists(db, tableName, columns, columnTypes, null);
    }

    /**
     * Create a table if it does not exist, with an optional set of table constraints (e.g. composite
     * primary key, unique column values).
     */
    public static void createTableIfNotExists(
            SQLiteDatabase db,
            String tableName,
            String[] columns,
            String[] columnTypes,
            String[] tableConstraints) {
        boolean hasTableConstraints = tableConstraints != null && tableConstraints.length > 0;
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        builder.append('(');
        for (int i = 0; i < columns.length; i++) {
            builder.append(columns[i]);
            builder.append(' ');
            builder.append(columnTypes[i]);
            if (hasTableConstraints || i + 1 < columns.length) {
                builder.append(',');
            }
        }

        if (hasTableConstraints) {
            for (int i = 0; i < tableConstraints.length; i++) {
                builder.append(tableConstraints[i]);
                if (i + 1 < tableConstraints.length) {
                    builder.append(',');
                }
            }
        }

        builder.append(");");
        db.execSQL(builder.toString());

        Log.d(TAG, "Creating table: " + tableName);
    }

    /**
     * Drop columns inside a table by reconstructing it.
     *
     * @param db The database that the table is in.
     * @param tableName The name of the table.
     * @param columns The columns that should be existing in the table after dropping.
     * @param columnTypes The types of columns corresponding to columns.
     */
    public static void dropTableColumnsByReconstructing(
            SQLiteDatabase db, String tableName, String[] columns, String[] columnTypes) {
        // (non-javadoc)
        // Step1: Rename the existing table with the unwanted colum as the old table.
        String oldTableName = tableName + "_old";
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + oldTableName + ";");
        Log.d(TAG, "Renaming " + tableName + " to " + oldTableName + ".");

        // (non-javadoc)
        // Step2: Create a new table with the original table name consisting of the desired
        // columns.
        createTableIfNotExists(db, tableName, columns, columnTypes);

        // (non-javadoc)
        // Step3: Copy each row from the old table to the new table.
        String columnsSeperated = TextUtils.join(",", columns);
        db.execSQL(
                "INSERT INTO "
                        + tableName
                        + "("
                        + columnsSeperated
                        + ") SELECT "
                        + columnsSeperated
                        + " FROM "
                        + oldTableName
                        + ";");
        db.execSQL("DROP TABLE IF EXISTS " + oldTableName + ";");
        Log.d(TAG, "Old table copied over to new table successfully.");
    }

    /**
     * Add a column to a table.
     *
     * @param db The database that the table is in.
     * @param tableName The name of the table.
     * @param column The column that should be added to the table.
     * @param columnType The type of the new column.
     * @param defaultStr A string defining the default value for the new column. May be empty.
     */
    public static void addColumnToTable(
            SQLiteDatabase db, String tableName, String column, String columnType, String defaultStr) {
        Log.d(TAG, "Adding column " + column + " to " + tableName + ".");

        String alterStr = "ALTER TABLE " + tableName + " ADD COLUMN " + column + " " + columnType;
        if (defaultStr != null && !defaultStr.isEmpty()) {
            alterStr += " DEFAULT " + defaultStr;
        }
        db.execSQL(alterStr + ";");
    }

    public static Long getRowCount(SQLiteDatabase db, String tableName) {
        return DatabaseUtils.queryNumEntries(db, tableName);
    }

    public static byte[] getBolbFromCursor(Cursor cursor, String column) {
        return cursor.getBlob(cursor.getColumnIndexOrThrow(column));
    }

    public static long getLongFromCursor(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(column));
    }

    public static int getIntFromCursor(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column));
    }

    public static String getStringFromCursor(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndexOrThrow(column));
    }

    public static boolean getBooleanFromCursor(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column)) != 0;
    }

    /** Get the column names of a table. */
    public static List<String> getTableColumns(SQLiteDatabase db, String tableName) {
        ArrayList<String> columns = new ArrayList<String>();
        String cmd = "PRAGMA table_info(" + tableName + ");";
        Cursor cur = db.rawQuery(cmd, null);

        while (cur.moveToNext()) {
            columns.add(cur.getString(cur.getColumnIndex("name")));
        }
        cur.close();

        return columns;
    }
}
