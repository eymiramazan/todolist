package com.example.todolist;

import java.util.UUID;

public class Reminder {

    private UUID mId;
    private String mTitle;
    private String details;
    private boolean isCompleted;

    public Reminder() {
        this(UUID.randomUUID());
    }

    public Reminder(UUID id){
        mId = id;
        isCompleted = false;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}
