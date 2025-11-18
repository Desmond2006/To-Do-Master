package com.todo.models;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Task {
    private final StringProperty taskText;
    private final ObjectProperty<LocalDate> deadline;
    private final BooleanProperty completed;

    public Task(){
        this(null, null);
    }

    public Task(String taskText, LocalDate deadline){
        this.taskText = new SimpleStringProperty(taskText);
        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
        this.completed = new SimpleBooleanProperty(false);
    }

    public String getTaskText(){
        return taskText.get();
    }

    public void setTaskText(String newTaskText){
        this.taskText.set(newTaskText);
    }

    public LocalDate getDeadline(){
        return deadline.get();
    }

    public void setDeadline(LocalDate newDeadline){
        this.deadline.set(newDeadline);
    }

    public boolean getCompleted(){
        return completed.get();
    }

    public void setCompleted(boolean isCompleted){
        this.completed.set(isCompleted);
    }


}
