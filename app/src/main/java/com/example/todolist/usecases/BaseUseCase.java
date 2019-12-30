package com.example.todolist.usecases;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public abstract class BaseUseCase<T> {
    private final CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public BaseUseCase(Scheduler executorThread, Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
        compositeDisposable = new CompositeDisposable();
    }

    public void execute(DisposableObserver<Response<T>> disposableObserver) {

        if (disposableObserver == null) {
            throw new IllegalArgumentException("disposableObserver must not be null");
        }

        final Observable<Response<T>> observable =
                this.createObservableUseCase().subscribeOn(executorThread).observeOn(uiThread);

        DisposableObserver observer = observable.subscribeWith(disposableObserver);
        compositeDisposable.add(observer);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected abstract Observable<Response<T>> createObservableUseCase();
}
