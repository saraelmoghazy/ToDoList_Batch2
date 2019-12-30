package com.example.todolist.response;

public class ErrorResponse {

    private ErrorResponse error;

    private String message;

    public ErrorResponse getError() {
        return error;
    }

    public String getMessage() {
        return error.message;
    }

    @Override
    public String toString() {
        return
                "ErrorResponse{" +
                        "error = '" + error + '\'' +
                        ",message = '" + error.message + '\'' +
                        "}";
    }
}