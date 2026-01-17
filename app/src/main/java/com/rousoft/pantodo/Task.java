package com.rousoft.pantodo;

public class Task {
    String title;
    String description;
    boolean isDone;
    boolean isSelected = false; // Untuk fitur multi-select

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isDone = false;
    }
}
