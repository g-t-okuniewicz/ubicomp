package com.example.gabi.lists;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.gabi.lists.ListsContract.*;

/**
 * Created by gabi on 12/10/17.
 */

public class ListsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lists.db";

    private static final String SQL_CREATE_ENTRIES;
    private static final String SQL_DELETE_ENTRIES;

    static {
        SQL_CREATE_ENTRIES = "CREATE TABLE " + ListsEntry.TABLE_NAME + " (" +
                ListsEntry._ID + " INTEGER PRIMARY KEY, " +
                ListsEntry.COLUMN_NAME_LISTNAME + " TEXT, " +
                ListsEntry.COLUMN_NAME_ITEMS + " TEXT)";
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ListsEntry.TABLE_NAME;
    }

    public ListsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
