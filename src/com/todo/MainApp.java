////package com.todo;
////
////import com.todo.models.Task;
////import com.todo.views.MainWindowController;
////import javafx.application.Application;
////import javafx.collections.FXCollections;
////import javafx.collections.ObservableList;
////import javafx.fxml.FXMLLoader;
////import javafx.scene.Scene;
////import javafx.scene.layout.AnchorPane;
////import javafx.scene.layout.StackPane;
////import javafx.stage.Stage;
//////import com.todo.views.RootWindowController;
////import java.io.IOException;
////import java.time.LocalDate;
////import com.todo.models.Note;
////
////
////
////public class MainApp extends Application{
////
////    private ObservableList<Task> taskData = FXCollections.observableArrayList();
////    private ObservableList<Note> noteData = FXCollections.observableArrayList();
////
////    private Stage primaryStage;
////
////
////
////    public ObservableList<Note> getNoteData() {
////        return noteData;
////    }
////
////    public ObservableList<Task> getTaskData(){
////        return taskData;
////    }
////
////    // В конструктор Main добавьте тестовые данные:
////    public MainApp() {
////        // Тестовые данные
////        taskData.add(new Task("Завершить проект JavaFX", LocalDate.now().plusDays(2)));
////        taskData.add(new Task("Купить продукты", LocalDate.now()));
////        taskData.add(new Task("Записаться к врачу", LocalDate.now().plusDays(5)));
////
////        noteData.add(new Note("Идеи для проекта", "Нужно реализовать:\n- Систему задач\n- Систему заметок\n- Настройки пользователя"));
////        noteData.add(new Note("Покупки", "Молоко, хлеб, яйца, фрукты, овощи"));
////        noteData.add(new Note("Важные контакты", "Иван: +7-999-123-45-67\nМария: maria@email.com"));
////    }
////
////    @Override
////    public void start(Stage primaryStage) {
////        this.primaryStage = primaryStage;
////        this.primaryStage.setTitle("To-Do Master");
////
////
////        initRootLayout();
////
////    }
////
////
////
////
////
////    public void initRootLayout(){
////        try{
////            FXMLLoader loader = new FXMLLoader();
////            loader.setLocation(MainApp.class.getResource("/com/todo/views/MainWindow.fxml"));
////            AnchorPane rootLayout = loader.load();
////
////            //отображаем сцену, содержащую корневой макет
////            Scene scene = new Scene(rootLayout);
////            primaryStage.setScene(scene);
////
////            //Даём контроллеру доступ к главному приложению
////            MainWindowController controller = loader.getController();
////            controller.setMainApp(this);
////
////            primaryStage.show();
////        }catch(IOException e){
////            e.printStackTrace();
////        }
////    }
////
////    public Stage getPrimaryStage() {
////        return primaryStage;
////    }
////
////
////
////    public static void main(String[] args){
////
////        launch(args);
////    }
////}
//package com.todo;
//
//import com.todo.models.Task;
//import com.todo.models.Note;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane; // Используем Pane вместо AnchorPane
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import java.io.IOException;
//import java.time.LocalDate;
//
//public class MainApp extends Application {
//
//    private ObservableList<Task> taskData = FXCollections.observableArrayList();
//    private ObservableList<Note> noteData = FXCollections.observableArrayList();
//
//    private Stage primaryStage;
//    private StackPane rootPane; // Основной контейнер для смены сцен
//    private Scene mainScene;
//
//    // Константы размеров окна
//    private static final double WINDOW_WIDTH = 900;
//    private static final double WINDOW_HEIGHT = 600;
//
//    public ObservableList<Note> getNoteData() {
//        return noteData;
//    }
//
//    public ObservableList<Task> getTaskData() {
//        return taskData;
//    }
//
//    // В конструктор Main добавьте тестовые данные:
//    public MainApp() {
//        // Тестовые данные
//        taskData.add(new Task("Завершить проект JavaFX", LocalDate.now().plusDays(2)));
//        taskData.add(new Task("Купить продукты", LocalDate.now()));
//        taskData.add(new Task("Записаться к врачу", LocalDate.now().plusDays(5)));
//
//        noteData.add(new Note("Идеи для проекта", "Нужно реализовать:\n- Систему задач\n- Систему заметок\n- Настройки пользователя"));
//        noteData.add(new Note("Покупки", "Молоко, хлеб, яйца, фрукты, овощи"));
//        noteData.add(new Note("Важные контакты", "Иван: +7-999-123-45-67\nМария: maria@email.com"));
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("To-Do Master");
//
//        // Создаем корневой контейнер StackPane для смены сцен
//        rootPane = new StackPane();
//        mainScene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);
//
//        // Настраиваем окно
//        primaryStage.setScene(mainScene);
//        primaryStage.setWidth(WINDOW_WIDTH);
//        primaryStage.setHeight(WINDOW_HEIGHT);
//        primaryStage.setMinWidth(700);
//        primaryStage.setMinHeight(500);
//
//        // Показываем форму логина при запуске
//        showLoginForm();
//
//        primaryStage.show();
//    }
//
//    /**
//     * Показать форму логина
//     */
//    public void showLoginForm() {
//        try {
//            // Загружаем FXML форму логина
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("/com/todo/views/LoginView.fxml"));
//
//            // Используем Pane (или Parent) вместо AnchorPane, так как формы используют VBox
//            javafx.scene.Parent loginForm = loader.load();
//
//            // Получаем контроллер
//            com.todo.views.LoginController controller = loader.getController();
//            controller.setMainApp(this);
//
//            // Очищаем контейнер и добавляем форму логина
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(loginForm);
//
//            // Обновляем заголовок окна
//            primaryStage.setTitle("To-Do Master - Вход");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить форму входа");
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Ошибка при загрузке формы: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Показать форму регистрации
//     */
//    public void showRegisterForm() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("/com/todo/views/RegisterView.fxml"));
//
//            // Используем Parent для совместимости с любым типом контейнера
//            javafx.scene.Parent registerForm = loader.load();
//
//            com.todo.views.RegisterController controller = loader.getController();
//            controller.setMainApp(this);
//
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(registerForm);
//
//            primaryStage.setTitle("To-Do Master - Регистрация");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить форму регистрации");
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Ошибка при загрузке формы: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Показать главное окно приложения
//     */
//    public void showMainApp() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("/com/todo/views/MainWindow.fxml"));
//
//            // Используем Parent для совместимости
//            javafx.scene.Parent mainWindow = loader.load();
//
//            // Даём контроллеру доступ к главному приложению
//            com.todo.views.MainWindowController controller = loader.getController();
//            controller.setMainApp(this);
//
//            // Очищаем и добавляем главное окно
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(mainWindow);
//
//            // Обновляем заголовок
//            primaryStage.setTitle("To-Do Master");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить главное окно");
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Ошибка при загрузке главного окна: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Показать сообщение об ошибке
//     */
//    private void showError(String title, String message) {
//        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
//                javafx.scene.control.Alert.AlertType.ERROR
//        );
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    public Stage getPrimaryStage() {
//        return primaryStage;
//    }
//
//    public static void main(String[] args) {
//        // Для JavaFX 17+ добавляем нативные разрешения
//        System.setProperty("javafx.allowNativeAccess", "true");
//        launch(args);
//    }
//}

package com.todo;

import com.todo.models.Note;
import com.todo.models.Task;
import com.todo.views.RegisterController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.todo.views.LoginController;
import java.time.LocalDate;

public class MainApp extends Application {

    private static MainApp instance;
    private Stage primaryStage;

    // Данные текущего пользователя
    private String currentUsername;
    private Long currentUserId;
    private String currentUserEmail;

    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private ObservableList<Note> noteData = FXCollections.observableArrayList();

    // ДОБАВЬТЕ ЭТИ ПОЛЯ:
    private StackPane rootPane;
    private Scene mainScene;
    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;

    // ДОБАВЬТЕ КОНСТРУКТОР:
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
        instance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("To-Do Master");

        // ИНИЦИАЛИЗИРУЙТЕ rootPane и mainScene:
        rootPane = new StackPane();
        mainScene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setScene(mainScene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);

        // Загружаем форму входа
        showLoginView();

        primaryStage.show();
    }

    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/LoginView.fxml"));
            Parent loginForm = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(loginForm);

            primaryStage.setTitle("To-Do Master - Вход");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", "Не удалось загрузить форму входа");
        }
    }

    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/RegisterView.fxml"));
            Parent registerForm = loader.load();

            RegisterController controller = loader.getController();
            controller.setMainApp(this);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(registerForm);

            primaryStage.setTitle("To-Do Master - Регистрация");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", "Не удалось загрузить форму регистрации");
        }
    }

    // ДОБАВЬТЕ ЭТОТ МЕТОД:
    public void showMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/MainWindow.fxml"));
            Parent mainWindow = loader.load();

            com.todo.views.MainWindowController controller = loader.getController();
            controller.setMainApp(this);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(mainWindow);

            primaryStage.setTitle("To-Do Master");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", "Не удалось загрузить главное окно");
        }
    }

    // ДОБАВЬТЕ ЭТОТ МЕТОД:
    private void showError(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR
        );
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ObservableList<Note> getNoteData() {
        return noteData;
    }

    public ObservableList<Task> getTaskData() {
        return taskData;
    }

    // Методы для управления данными пользователя
    public void setCurrentUser(String username, Long userId, String email) {
        this.currentUsername = username;
        this.currentUserId = userId;
        this.currentUserEmail = email;
    }

    public void clearCurrentUser() {
        this.currentUsername = null;
        this.currentUserId = null;
        this.currentUserEmail = null;
    }

    // Getters
    public String getCurrentUsername() {
        return currentUsername;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static MainApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // Для JavaFX 17+ добавляем нативные разрешения
        System.setProperty("javafx.allowNativeAccess", "true");
        launch(args);
    }
}