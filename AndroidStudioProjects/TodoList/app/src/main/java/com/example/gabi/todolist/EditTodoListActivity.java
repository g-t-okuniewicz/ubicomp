package com.example.gabi.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTodoListActivity extends AppCompatActivity {


    private static final String LIST_TITLE_KEY = "todo_list_title";

    private EditText titleEditText, todosEditText;
    private Button deleteButton;

    private DBHelper dbHelper;

    private TodoList todoList;
    private boolean isExistingTodoList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_list);

        dbHelper = new DBHelper(this);

        titleEditText = (EditText)findViewById(R.id.titleEditText);
        todosEditText = (EditText)findViewById(R.id.todosEditText);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setEnabled(false);


        String listTitle = null;
        if(getIntent().hasExtra(LIST_TITLE_KEY)) {
            listTitle = (String) getIntent().getExtras().get(LIST_TITLE_KEY);
        }

        if(listTitle != null) {
            todoList = dbHelper.getTodoListByTitle(listTitle);
            isExistingTodoList = true;
            deleteButton.setEnabled(true);
            titleEditText.setText(todoList.getTitle());
            todosEditText.setText(todoList.getTodos());
        }

    }

    public void saveButtonAction(View view) {

        if(!isExistingTodoList) {
            if(dbHelper.insertData(titleEditText.getText().toString(),
                    todosEditText.getText().toString())) {
                showMessage("Entry added ");
                finish();
            }
            else
                showMessage("Entry couldn't be added");
        }
        else {
            todoList.setTitle(titleEditText.getText().toString());
            todoList.setTodos(todosEditText.getText().toString());
            dbHelper.updateTodoList(todoList);
            showMessage("Updated: " + todoList.getTitle() + "\n" + todoList.getTodos());
            finish();
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void cancelButtonAction(View view) {
        finish();
    }

    public void deleteButtonAction(View view) {
        dbHelper.deleteTodoList(todoList);
        finish();
    }
}
