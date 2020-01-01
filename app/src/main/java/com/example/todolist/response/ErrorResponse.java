package com.example.todolist.response;

public class ErrorResponse {


    private String message;


    @Override
    public String toString() {
        return
                "ErrorResponse{" +
                        "error = '" + message + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}