package com.todo.models;

import java.time.LocalDate;

public class Task {
    private Long id; // Добавляем поле для хранения ID с сервера
    private String taskText;
    private LocalDate deadline;
    private boolean completed;

    // Конструкторы
    public Task() {}

    public Task(String taskText, LocalDate deadline) {
        this.taskText = taskText;
        this.deadline = deadline;
        this.completed = false;
    }

    public Task(Long id, String taskText, LocalDate deadline, boolean completed) {
        this.id = id;
        this.taskText = taskText;
        this.deadline = deadline;
        this.completed = completed;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
