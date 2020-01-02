package com.example.todolist.repository;

import com.example.todolist.models.StatusToDoResponse;
import com.example.todolist.models.Todo;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<List<Todo>> getCompletedTodoList();

    Observable<List<Todo>> getNotCompletedTodoList();

    Observable<Todo> postTodo(Todo todo);

    Observable<StatusToDoResponse> updateTodo(Todo todo);

    Observable<Void> deleteTodo(long id);
}
