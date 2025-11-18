package com.todo.views;

import com.todo.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import com.todo.views.AllTasksViewController;
import java.io.IOException;

public class RootWindowController {
    //ссылка на главное приложение
    private MainApp mainApp;

    //правая часть с контентом
    @FXML
    private AnchorPane contentArea;


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleAllTasks(){
        loadView("/com/todo/views/AllTasksView.fxml");
    }

    @FXML
    private void handleToday(){

    }

    @FXML
    private void handleUpcoming(){

    }

    @FXML
    private void handleAddTaskFolder(){

    }

    @FXML
    private void handleAllNotes(){
        loadView("/com/todo/views/AllNotesView.fxml");
    }

    @FXML
    private void handleAddNoteFolder(){

    }

    @FXML
    private void handleSettings(){

    }

    @FXML
    private void handleProfile(){

    }

    //загружаем fxml представления в правую часть
    private void loadView(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();

            //очищаем текущий контент и добавляем новое представление
            contentArea.getChildren().setAll(view);

            //растягиваем наше представление
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            Object controller = loader.getController();


            // TODO обрабтка передачи управления в другие контроллеры
            //передаём ссылку на mainApp в загруженный контроллер
            // Если это контроллер задач, передаем ему данные
            if (controller instanceof AllTasksViewController) {
                ((AllTasksViewController) controller).setMainApp(mainApp);
            }else if(controller instanceof AllNotesViewController){
                ((AllNotesViewController) controller).setMainApp(mainApp);
            }

        }catch (IOException e){
            e.printStackTrace();
            showErrorAlert("Не удалось загрузить представление: " + fxmlPath);
            showPlaceholder("");
        }
    }

    private void showPlaceholder(String text) {
        javafx.scene.control.Label placeholder = new javafx.scene.control.Label(text);
        placeholder.setStyle("-fx-font-size: 18; -fx-text-fill: #64748b; -fx-padding: 20;");
        contentArea.getChildren().setAll(placeholder);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
