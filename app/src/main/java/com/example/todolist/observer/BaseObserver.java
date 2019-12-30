package com.example.todolist.observer;

import android.util.Log;

import com.example.todolist.utils.NetworkState;
import com.example.todolist.viewmodels.BaseViewModel;

import io.reactivex.observers.DisposableObserver;

public class BaseObserver<T> extends DisposableObserver<T> {
    private static final String TAG = "BaseObserver";
    private BaseViewModel baseViewModel;

    public BaseObserver(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    @Override
    protected void onStart() {
        baseViewModel.setObserverNetworkState(NetworkState.loading(true));
    }

    @Override
    public void onNext(T t) {
        baseViewModel.setObserverNetworkState(NetworkState.success(t));
    }

    @Override
    public void onError(Throwable e) {
        baseViewModel.setObserverNetworkState(NetworkState.error(e.getMessage()));
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }
}
