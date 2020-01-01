package com.example.todolist.observer;

import android.util.Log;

import com.example.todolist.response.ErrorResponse;
import com.example.todolist.response.RetrofitException;
import com.example.todolist.utils.NetworkState;
import com.example.todolist.viewmodels.BaseViewModel;

import java.io.IOException;

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
        Log.d(TAG, "onNext: " + t.toString());
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: throwable: " + e.getMessage());
        RetrofitException retrofitException = (RetrofitException) e;
        RetrofitException.Kind kind = retrofitException.getKind();
        Log.d(TAG, "onError: kind: " + kind.toString());
        if (kind == RetrofitException.Kind.HTTP) {
            ErrorResponse errorResponse;
            try {
                errorResponse = retrofitException.getErrorBodyAs(ErrorResponse.class);
                Log.d(TAG, "onError: errorResponse: " + errorResponse.toString());
            } catch (IOException ex) {
                Log.d(TAG, "onError: " + ex.getMessage());
            }
        } else {
            Log.d(TAG, "onError: message: " + retrofitException.getMessage());
        }
        baseViewModel.setObserverNetworkState(NetworkState.error(e.getMessage()));
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }
}
