package com.example.gabi.lists;

import android.provider.BaseColumns;

/**
 * Created by gabi on 12/10/17.
 */

public final class ListsContract {

    private ListsContract() {}

    public static class ListsEntry implements BaseColumns {
        public static final String TABLE_NAME = "lists";
        public static final String COLUMN_NAME_LISTNAME = "listname";
        public static final String COLUMN_NAME_ITEMS = "items";
    }

}
