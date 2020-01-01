package com.example.todolist.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.models.Todo;
import com.example.todolist.observer.BaseObserver;
import com.example.todolist.usecases.DeleteTodoUseCase;
import com.example.todolist.usecases.GetCompletedTodoUseCase;
import com.example.todolist.usecases.GetNotCompletedTodoUseCase;
import com.example.todolist.usecases.PostTodoUseCase;
import com.example.todolist.usecases.UpdateTodoUseCase;
import com.example.todolist.utils.NetworkState;

import javax.inject.Inject;

public class BaseViewModel extends ViewModel {
    private static final String TAG = BaseViewModel.class.getSimpleName();
    private MutableLiveData<NetworkState> networkState;
    private BaseObserver baseObserver;
    private GetCompletedTodoUseCase getCompletedTodoUseCase;
    private GetNotCompletedTodoUseCase getNotCompletedTodoUseCase;
    private PostTodoUseCase postTodoUseCase;
    private UpdateTodoUseCase updateTodoUseCase;
    private DeleteTodoUseCase deleteTodoUseCase;

    @Inject
    public BaseViewModel(GetCompletedTodoUseCase getCompletedTodoUseCase,
                         GetNotCompletedTodoUseCase getNotCompletedTodoUseCase,
                         PostTodoUseCase postTodoUseCase,
                         UpdateTodoUseCase updateTodoUseCase,
                         DeleteTodoUseCase deleteTodoUseCase) {
        networkState = new MutableLiveData<>();
        this.getCompletedTodoUseCase = getCompletedTodoUseCase;
        this.getNotCompletedTodoUseCase = getNotCompletedTodoUseCase;
        this.postTodoUseCase = postTodoUseCase;
        this.updateTodoUseCase = updateTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
        getNotCompletedTodoUseCase();
    }

    public void getCompletedTodoUseCase() {
        baseObserver = new BaseObserver(this);
        getCompletedTodoUseCase.execute(baseObserver);
    }

    private void getNotCompletedTodoUseCase() {
        baseObserver = new BaseObserver(this);
        getNotCompletedTodoUseCase.execute(baseObserver);
    }

    public void postTodoUseCase(Todo todo) {
        postTodoUseCase.setTodo(todo);
        baseObserver = new BaseObserver(this);
        postTodoUseCase.execute(baseObserver);
    }

    public void updateTodoUseCase(Todo todo) {
        updateTodoUseCase.setTodo(todo);
        baseObserver = new BaseObserver(this);
        updateTodoUseCase.execute(baseObserver);
    }

    public void deleteTodoUseCase(Todo todo) {
        deleteTodoUseCase.setTodo(todo);
        baseObserver = new BaseObserver(this);
        deleteTodoUseCase.execute(baseObserver);
    }

    public void setObserverNetworkState(NetworkState networkState) {
        this.networkState.setValue(networkState);
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void retry() {
        getCompletedTodoUseCase();
    }

    @Override
    protected void onCleared() {
        getCompletedTodoUseCase.dispose();
        getNotCompletedTodoUseCase.dispose();
        updateTodoUseCase.dispose();
        deleteTodoUseCase.dispose();
    }
}