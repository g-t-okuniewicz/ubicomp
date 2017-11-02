package com.example.gabi.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabi on 01/11/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo_list.db";
    private static final String TABLE_NAME = "todo_list_table";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_TITLE = "name";
    private static final String COLUMN_NAME_TODOS = "todos";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_TODOS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(String title, String todos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_TODOS, todos );

        long newRowId = db.insert(TABLE_NAME, null, values);

        if(newRowId == -1)
            return false;
        else
            return true;
    }

    public List<TodoList> getAllTodoLists() {
        List<TodoList> allTodoLists = new ArrayList<TodoList>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(c.getCount()==0)
            return null;

        while(c.moveToNext()) {
            TodoList list = new TodoList(c.getString(1), c.getString(2));
            allTodoLists.add(list);
        }

        return allTodoLists;
    }

    public TodoList getTodoListByTitle(String todoListTitle) {

        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_TITLE,
                COLUMN_NAME_TODOS
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { todoListTitle };
        Cursor c = db.query(
                TABLE_NAME,     // the table to query
                projection,     // the columns to return
                selection,      // the columns for the WHERE clause
                selectionArgs,  // the values for the WHERE clause
                null,           // don't group the rows
                null,           // don't filter by row groups
                null            // sort order
        );

        if(c.getCount() == 0)
            return null;
        else {
            c.moveToFirst();
            long id = c.getLong(c.getColumnIndex(COLUMN_NAME_ID));
            String title = c.getString(c.getColumnIndex(COLUMN_NAME_TITLE));
            String todos = c.getString(c.getColumnIndex(COLUMN_NAME_TODOS));
            return new TodoList(id, title, todos);
        }

    }

    public void updateTodoList(TodoList todoList) {

        SQLiteDatabase db = this.getWritableDatabase();

        // new values
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, todoList.getTitle());
        values.put(COLUMN_NAME_TODOS, todoList.getTodos());

        // which rows to update
        String selection = COLUMN_NAME_ID + " = ?";
        String id = Long.toString(todoList.getId());
        String[] selectionArgs = { id };

        db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void deleteTodoList(TodoList todoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_NAME_ID + " = ?";
        String id = Long.toString(todoList.getId());
        String[] selectionArgs = { id };

        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
