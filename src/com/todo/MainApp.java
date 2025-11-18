package com.todo;

import com.todo.models.Task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.todo.views.RootWindowController;
import java.io.IOException;
import java.time.LocalDate;
import com.todo.models.Note;

public class MainApp extends Application{

    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private ObservableList<Note> noteData = FXCollections.observableArrayList();

    private Stage primaryStage;



    public ObservableList<Note> getNoteData() {
        return noteData;
    }

    public ObservableList<Task> getTaskData(){
        return taskData;
    }

    // В конструктор Main добавьте тестовые данные:
    public MainApp() {
        // Тестовые данные
        taskData.add(new Task("Завершить проект JavaFX", LocalDate.now().plusDays(2)));
        taskData.add(new Task("Купить продукты", LocalDate.now()));
        taskData.add(new Task("Записаться к врачу", LocalDate.now().plusDays(5)));

        noteData.add(new Note("Идеи для проекта", "Нужно реализовать:\n- Систему задач\n- Систему заметок\n- Настройки пользователя"));
        noteData.add(new Note("Покупки", "Молоко, хлеб, яйца, фрукты, овощи"));
        noteData.add(new Note("Важные контакты", "Иван: +7-999-123-45-67\nМария: maria@email.com"));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("To-Do Master");

        initRootLayout();
    }


    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/todo/views/rootWindow.fxml"));
            AnchorPane rootLayout = loader.load();

            //отображаем сцену, содержащую корневой макет
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //Даём контроллеру доступ к главному приложению
            RootWindowController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }



    public static void main(String[] args){
        launch(args);
    }
}