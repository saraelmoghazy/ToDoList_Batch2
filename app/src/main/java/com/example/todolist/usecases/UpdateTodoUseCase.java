package com.example.todolist.usecases;

import com.example.todolist.models.Todo;
import com.example.todolist.repository.Repository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Response;

public class UpdateTodoUseCase extends BaseUseCase<Void> {
    private Repository repository;
    private Todo todo;

    @Inject
    public UpdateTodoUseCase(@Named("executor_thread") Scheduler executorThread,
                             @Named("ui_thread") Scheduler uiThread,
                             Repository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    protected Observable<Void> createObservableUseCase() {
        return repository.updateTodo(todo);
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
}
