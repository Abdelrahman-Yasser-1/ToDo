package com.example.to_do.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NotNull
    private String title;

    private String desctiption;

    @NotNull
    private String date;
    @NotNull
    private String time;
    @NotNull
    private boolean status;

    public Task(String title, String desctiption, String date, String time, boolean status) {
        this.title = title;
        this.desctiption = desctiption;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    @NotNull
    public String getTime() {
        return time;
    }

    public void setTime(@NotNull String time) {
        this.time = time;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
