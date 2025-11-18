package com.todo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public Note(String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }

    // Сеттеры
    public void setTitle(String title) {
        this.title = title;
        this.modifiedDate = LocalDateTime.now();
    }

    public void setContent(String content) {
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
    }

    // Вспомогательные методы
    public String getFormattedCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getFormattedModifiedDate() {
        return modifiedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getPreview() {
        if (content == null || content.isEmpty()) {
            return "Нет текста";
        }
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }

    @Override
    public String toString() {
        return title + " [" + getFormattedCreatedDate() + "]";
    }
}