package com.example.todolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Todo implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("isCompleted")
    private boolean isCompleted;

    private Todo(Builder builder) {
        title = builder.title;
        content = builder.content;
        isCompleted = builder.isCompleted;
        id = builder.id;
    }

    public static class Builder {
        private String title;
        private String content;
        private boolean isCompleted;
        private long id;
        private static long count = 0;

        public Builder setTitle(String title) {
            this.title = title;
            id = count++;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }


    protected Todo(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        isCompleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
