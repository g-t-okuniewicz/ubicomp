package com.example.gabi.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private static final String LIST_TITLE_KEY = "todo_list_title";

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        listView = (ListView) findViewById(R.id.listView);

        updateListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    public void newListButtonAction(View view) {
        Intent intent = new Intent(this, EditTodoListActivity.class);
        startActivity(intent);
    }

    public void deleteAllButtonAction(View view) {
        dbHelper.deleteAll();
        updateListView();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void updateListView() {
        final List<TodoList> allTodoLists = dbHelper.getAllTodoLists();

        String[] values;
        boolean isEmpty = true;

        if(allTodoLists == null) {
            values = new String[1];
            values[0] = "Nothing to show";
        }
        else {
            values = new String[allTodoLists.size()];
            for (int i = 0; i < allTodoLists.size(); i++) {
                values[i] = allTodoLists.get(i).getTitle() + ":\n" + allTodoLists.get(i).getTodos();
            }
            isEmpty = false;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                values
        );
        listView.setAdapter(adapter);

        if(!isEmpty) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String todoListTitle = allTodoLists.get(i).getTitle();

                    startEditActivity(todoListTitle);
                }
            });
        }
    }

    protected void startEditActivity(String todoListTitle) {
        Intent intent = new Intent(this, EditTodoListActivity.class);
        intent.putExtra(LIST_TITLE_KEY, todoListTitle);
        startActivity(intent);
    }


    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
