package com.example.todolist.usecases;

import com.example.todolist.models.Todo;
import com.example.todolist.repository.Repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Response;

public class GetNotCompletedTodoUseCase extends BaseUseCase<List<Todo>> {
    private Repository repository;

    @Inject
    public GetNotCompletedTodoUseCase(@Named("executor_thread") Scheduler executorThread,
                                      @Named("ui_thread") Scheduler uiThread,
                                      Repository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    protected Observable<Response<List<Todo>>> createObservableUseCase() {
        return this.repository.getNotCompletedTodoList();
    }
}