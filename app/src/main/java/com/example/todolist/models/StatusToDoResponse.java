package com.example.todolist.models;

import com.google.gson.annotations.SerializedName;

public class StatusToDoResponse {

    @SerializedName("status")
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
