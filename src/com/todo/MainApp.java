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
///
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

//package com.todo;
//
//import com.todo.models.Note;
//import com.todo.models.Task;
//import com.todo.views.RegisterController;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import com.todo.views.LoginController;
//import java.time.LocalDate;
//
//public class MainApp extends Application {
//
//    private static MainApp instance;
//    private Stage primaryStage;
//
//    // Данные текущего пользователя
//    private String currentUsername;
//    private Long currentUserId;
//    private String currentUserEmail;
//
//    private ObservableList<Task> taskData = FXCollections.observableArrayList();
//    private ObservableList<Note> noteData = FXCollections.observableArrayList();
//
//    // ДОБАВЬТЕ ЭТИ ПОЛЯ:
//    private StackPane rootPane;
//    private Scene mainScene;
//    private static final double WINDOW_WIDTH = 900;
//    private static final double WINDOW_HEIGHT = 600;
//
//    // ДОБАВЬТЕ КОНСТРУКТОР:
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
//        instance = this;
//        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("To-Do Master");
//
//        // ИНИЦИАЛИЗИРУЙТЕ rootPane и mainScene:
//        rootPane = new StackPane();
//        mainScene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);
//
//        primaryStage.setScene(mainScene);
//        primaryStage.setWidth(WINDOW_WIDTH);
//        primaryStage.setHeight(WINDOW_HEIGHT);
//        primaryStage.setMinWidth(700);
//        primaryStage.setMinHeight(500);
//
//        // Загружаем форму входа
//        showLoginView();
//
//        primaryStage.show();
//    }
//
//    public void showLoginView() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/LoginView.fxml"));
//            Parent loginForm = loader.load();
//
//            LoginController controller = loader.getController();
//            controller.setMainApp(this);
//
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(loginForm);
//
//            primaryStage.setTitle("To-Do Master - Вход");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить форму входа");
//        }
//    }
//
//    public void showRegisterView() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/RegisterView.fxml"));
//            Parent registerForm = loader.load();
//
//            RegisterController controller = loader.getController();
//            controller.setMainApp(this);
//
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(registerForm);
//
//            primaryStage.setTitle("To-Do Master - Регистрация");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить форму регистрации");
//        }
//    }
//
//    // ДОБАВЬТЕ ЭТОТ МЕТОД:
//    public void showMainApp() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/MainWindow.fxml"));
//            Parent mainWindow = loader.load();
//
//            com.todo.views.MainWindowController controller = loader.getController();
//            controller.setMainApp(this);
//
//            rootPane.getChildren().clear();
//            rootPane.getChildren().add(mainWindow);
//
//            primaryStage.setTitle("To-Do Master");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Ошибка", "Не удалось загрузить главное окно");
//        }
//    }
//
//    // ДОБАВЬТЕ ЭТОТ МЕТОД:
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
//    public ObservableList<Note> getNoteData() {
//        return noteData;
//    }
//
//    public ObservableList<Task> getTaskData() {
//        return taskData;
//    }
//
//    // Методы для управления данными пользователя
//    public void setCurrentUser(String username, Long userId, String email) {
//        this.currentUsername = username;
//        this.currentUserId = userId;
//        this.currentUserEmail = email;
//    }
//
//    public void clearCurrentUser() {
//        this.currentUsername = null;
//        this.currentUserId = null;
//        this.currentUserEmail = null;
//    }
//
//    // Getters
//    public String getCurrentUsername() {
//        return currentUsername;
//    }
//
//    public Long getCurrentUserId() {
//        return currentUserId;
//    }
//
//    public String getCurrentUserEmail() {
//        return currentUserEmail;
//    }
//
//    public Stage getPrimaryStage() {
//        return primaryStage;
//    }
//
//    public static MainApp getInstance() {
//        return instance;
//    }
//
//    public static void main(String[] args) {
//        // Для JavaFX 17+ добавляем нативные разрешения
//        System.setProperty("javafx.allowNativeAccess", "true");
//        launch(args);
//    }
//}

//
package com.todo;

import java.util.ArrayList;
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
import com.todo.api.TaskApiClient;
import com.todo.api.NoteApiClient; // Добавляем импорт

import java.time.LocalDate;
import java.util.List;

public class MainApp extends Application {

    private static MainApp instance;
    private Stage primaryStage;

    // Данные текущего пользователя
    private String currentUsername;
    private Long currentUserId;
    private String currentUserEmail;

    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private ObservableList<Note> noteData = FXCollections.observableArrayList();

    private StackPane rootPane;
    private Scene mainScene;
    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;

    public MainApp() {
        // Удаляем тестовые данные из конструктора
        // Данные будут загружаться с сервера
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("To-Do Master");

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

    public void showMainApp() {
        try {
            // Загружаем данные с сервера
            loadUserDataFromServer();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/MainWindow.fxml"));
            Parent mainWindow = loader.load();

            com.todo.views.MainWindowController controller = loader.getController();
            controller.setMainApp(this);

            rootPane.getChildren().clear();
            rootPane.getChildren().add(mainWindow);

            primaryStage.setTitle("To-Do Master - " + currentUsername);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", "Не удалось загрузить главное окно");
        }
    }

    /**
     * Загрузить данные пользователя с сервера
     */
    private void loadUserDataFromServer() {
        if (currentUserId == null) {
            return;
        }

        // Загружаем задачи
        loadUserTasksFromServer();

        // Загружаем заметки
        loadUserNotesFromServer();
    }

    /**
     * Загрузить задачи пользователя с сервера
     */
    private void loadUserTasksFromServer() {
        if (currentUserId == null) {
            return;
        }

        taskData.clear();
        List<Task> tasks = TaskApiClient.loadUserTasks(currentUserId);
        taskData.addAll(tasks);
    }

    /**
     * Загрузить заметки пользователя с сервера
     */
    private void loadUserNotesFromServer() {
        if (currentUserId == null) {
            return;
        }

        noteData.clear();
        List<Note> notes = NoteApiClient.loadUserNotes(currentUserId);
        noteData.addAll(notes);
    }

    // === Методы для работы с задачами ===

    /**
     * Добавить новую задачу (синхронизировать с сервером)
     */
    public void addTask(Task task) {
        if (currentUserId == null) {
            return;
        }

        boolean success = TaskApiClient.createTask(task, currentUserId);

        if (success) {
            taskData.add(task);
        } else {
            showError("Ошибка", "Не удалось сохранить задачу на сервере");
        }
    }

    /**
     * Обновить задачу (синхронизировать с сервером)
     */
    public void updateTask(Task task) {
        if (currentUserId == null) {
            return;
        }

        boolean success = TaskApiClient.updateTask(task, currentUserId);

        if (!success) {
            showError("Ошибка", "Не удалось обновить задачу на сервере");
        }
    }

    /**
     * Удалить задачу (синхронизировать с сервером)
     */
    public void deleteTask(Task task) {
        if (currentUserId == null) {
            return;
        }

        boolean success = TaskApiClient.deleteTask(task.getId(), currentUserId);

        if (success) {
            taskData.remove(task);
        } else {
            showError("Ошибка", "Не удалось удалить задачу на сервере");
        }
    }

    // === Методы для работы с заметками ===

    /**
     * Добавить новую заметку (синхронизировать с сервером)
     */
    public void addNote(Note note) {
        if (currentUserId == null) {
            return;
        }

        boolean success = NoteApiClient.createNote(note, currentUserId);

        if (success) {
            noteData.add(note);
        } else {
            showError("Ошибка", "Не удалось сохранить заметку на сервере");
        }
    }

    /**
     * Обновить заметку (синхронизировать с сервером)
     */
    public void updateNote(Note note) {
        if (currentUserId == null) {
            return;
        }

        boolean success = NoteApiClient.updateNote(note, currentUserId);

        if (!success) {
            showError("Ошибка", "Не удалось обновить заметку на сервере");
        }
    }

    /**
     * Удалить заметку (синхронизировать с сервером)
     */
    public void deleteNote(Note note) {
        if (currentUserId == null) {
            return;
        }

        boolean success = NoteApiClient.deleteNote(note.getId(), currentUserId);

        if (success) {
            noteData.remove(note);
        } else {
            showError("Ошибка", "Не удалось удалить заметку на сервере");
        }
    }

    /**
     * Поиск заметок
     */
    public List<Note> searchNotes(String query) {
        if (currentUserId == null) {
            return new ArrayList<>();
        }

        return NoteApiClient.searchNotes(currentUserId, query);
    }

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

        // Загружаем данные при входе
        loadUserDataFromServer();
    }

    public void clearCurrentUser() {
        this.currentUsername = null;
        this.currentUserId = null;
        this.currentUserEmail = null;
        taskData.clear(); // Очищаем задачи при выходе
        noteData.clear(); // Очищаем заметки при выходе
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
        System.setProperty("javafx.allowNativeAccess", "true");
        launch(args);
    }
}