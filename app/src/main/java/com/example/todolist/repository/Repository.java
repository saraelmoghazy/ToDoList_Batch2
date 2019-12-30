package com.example.todolist.repository;

import com.example.todolist.models.Todo;

import io.reactivex.Observable;

public interface Repository<T> {
    Observable<T> getCompletedTodoList();

    Observable<T> getNotCompletedTodoList();

    Observable<T> postTodo(Todo todo);

    Observable<T> updateTodo(Todo todo);

    Observable<T> deleteTodo(long id);
}
