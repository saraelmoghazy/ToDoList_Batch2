package com.example.todolist.repository;

import com.example.todolist.models.Todo;
import com.example.todolist.network.TodoApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

public class TodoRepository implements Repository {

    private TodoApi todoApi;

    @Inject
    public TodoRepository(TodoApi todoApi) {
        this.todoApi = todoApi;
    }

    @Override
    public Observable<List<Todo>> getCompletedTodoList() {
        return todoApi.getCompletedTodos();
    }

    @Override
    public Observable<List<Todo>> getNotCompletedTodoList() {
        return todoApi.getNotCompletedTodos();
    }

    @Override
    public Observable postTodo(Todo todo) {
        return todoApi.postTodo(todo);
    }

    @Override
    public Observable updateTodo(Todo todo) {
        return todoApi.updateTodo(todo);
    }

    @Override
    public Observable deleteTodo(long id) {
        return todoApi.deleteTodo(id);
    }
}
