package com.example.todolist.network;

import com.example.todolist.models.Todo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoApi {
    @GET("todos/notCompleted")
    Observable<List<Todo>> getNotCompletedTodos();

    @GET("todos/completed")
    Observable<List<Todo>> getCompletedTodos();

    @POST("todos")
    Observable<Void> postTodo(@Body Todo todo);

    @PUT("todos")
    Observable<Void> updateTodo(@Body Todo todo);

    @DELETE("todos/{id}")
    Observable<Void> deleteTodo(@Path("id") long id);
}
