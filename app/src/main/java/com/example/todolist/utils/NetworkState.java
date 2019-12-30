package com.example.todolist.utils;

public class NetworkState<T> {
    public final Status status;
    public final T data;
    public final String message;
    public final boolean isLoading;

    public NetworkState(Status status, T data, String message, boolean isLoading) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.isLoading = isLoading;
    }

    public static <T> NetworkState<T> success(T data) {
        return new NetworkState<>(Status.SUCCESS, data, null, false);
    }

    public static <T> NetworkState<T> error(String message) {
        return new NetworkState<>(Status.ERROR, null, message, false);
    }

    public static <T> NetworkState<T> loading(boolean isLoading) {
        return new NetworkState<>(Status.LOADING, null, null, isLoading);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
