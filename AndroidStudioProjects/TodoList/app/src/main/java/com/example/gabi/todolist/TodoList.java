package com.example.gabi.todolist;

import java.util.List;

/**
 * Created by gabi on 01/11/17.
 */

public class TodoList {
    private long id = -1;
    private String title;
    private String todos;

    public TodoList(String title, String todos) {
        this.title = title;
        this.todos = todos;
    }

    public TodoList(long id, String title, String todos) {
        this.id = id;
        this.title = title;
        this.todos = todos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }
}
