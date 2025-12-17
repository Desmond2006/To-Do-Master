//package com.todo.views;
//
//import com.todo.MainApp;
//import com.todo.utils.HttpClientUtil;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class LoginController {
//
//    @FXML
//    private TextField usernameField;
//
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    private CheckBox rememberCheckBox;
//
//    @FXML
//    private Button loginButton;
//
//    @FXML
//    private Button registerButton;
//
//    @FXML
//    private Label messageLabel;
//
//    @FXML
//    private Label usernameError;
//
//    @FXML
//    private Label passwordError;
//
//    private MainApp mainApp;
//
//    @FXML
//    private void initialize() {
//        // Скрываем все сообщения об ошибках
//        messageLabel.setVisible(false);
//        usernameError.setVisible(false);
//        passwordError.setVisible(false);
//
//        // Обработчик Enter в полях
//        usernameField.setOnAction(e -> handleLogin());
//        passwordField.setOnAction(e -> handleLogin());
//
//        // Валидация в реальном времени
//        setupValidation();
//
//        // Загрузка сохраненных данных, если есть
//        loadSavedCredentials();
//    }
//
//    private void setupValidation() {
//        // Проверка имени пользователя
//        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal.isEmpty()) {
//                showError(usernameError, "Обязательное поле");
//            } else if (newVal.length() < 3) {
//                showError(usernameError, "Минимум 3 символа");
//            } else {
//                hideError(usernameError);
//            }
//        });
//
//        // Проверка пароля
//        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal.isEmpty()) {
//                showError(passwordError, "Обязательное поле");
//            } else if (newVal.length() < 6) {
//                showError(passwordError, "Минимум 6 символов");
//            } else {
//                hideError(passwordError);
//            }
//        });
//    }
//
//    @FXML
//    private void handleLogin() {
//        // Сбрасываем сообщения
//        messageLabel.setVisible(false);
//
//        // Проверяем форму
//        if (!validateForm()) {
//            return;
//        }
//
//        // Получаем данные из полей
//        String username = usernameField.getText().trim();
//        String password = passwordField.getText();
//
//        // Отправляем запрос на сервер
//        try {
//            // Создаем JSON объект для входа
//            Map<String, Object> loginData = new HashMap<>();
//            loginData.put("username", username);
//            loginData.put("password", password);
//
//            System.out.println("Отправка запроса на вход для пользователя: " + username);
//
//            // Отправляем POST запрос
//            Map<String, Object> response = HttpClientUtil.post(
//                    "http://localhost:8080/api/auth/login",
//                    loginData
//            );
//
//            System.out.println("Ответ сервера: " + response);
//
//            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
//                showMessage("Вход выполнен успешно!", "green");
//
//                // Сохраняем данные если отмечено
//                if (rememberCheckBox.isSelected()) {
//                    saveCredentials(username, password);
//                } else {
//                    clearSavedCredentials();
//                }
//
//                // Получаем данные пользователя из ответа
//                Long userId = response.get("userId") != null ?
//                        ((Number) response.get("userId")).longValue() : null;
//                String userEmail = (String) response.get("email");
//
//                // Здесь можно сохранить токен, если сервер его возвращает
//                // String token = (String) response.get("token");
//
//                // Сохраняем данные пользователя в MainApp
//                if (mainApp != null) {
//                    mainApp.setCurrentUser(username, userId, userEmail);
//                }
//
//                // Задержка перед переходом на главное окно
//                new Thread(() -> {
//                    try {
//                        Thread.sleep(1000); // 1 секунда
//                        javafx.application.Platform.runLater(this::showMainWindow);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//
//            } else {
//                String errorMsg = (String) response.getOrDefault("message", "Ошибка входа");
//                showMessage("Ошибка: " + errorMsg, "red");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Ошибка сети: " + e.getMessage());
//            showMessage("Ошибка соединения с сервером: " + e.getMessage(), "red");
//        }
//    }
//
//    @FXML
//    private void handleRegister() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/RegisterView.fxml"));
//            Parent root = loader.load();
//
//            // Получаем текущее окно
//            Stage stage = (Stage) registerButton.getScene().getWindow();
//
//            // Настраиваем контроллер
//            RegisterController controller = loader.getController();
//            controller.setMainApp(mainApp);
//
//            // Показываем окно регистрации
//            stage.setScene(new Scene(root));
//            stage.setTitle("Регистрация");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showMessage("Ошибка загрузки формы: " + e.getMessage(), "red");
//        }
//    }
//
//    private boolean validateForm() {
//        boolean isValid = true;
//
//        // Проверка имени пользователя
//        String username = usernameField.getText().trim();
//        if (username.isEmpty()) {
//            showError(usernameError, "Обязательное поле");
//            isValid = false;
//        } else if (username.length() < 3) {
//            showError(usernameError, "Минимум 3 символа");
//            isValid = false;
//        }
//
//        // Проверка пароля
//        String password = passwordField.getText();
//        if (password.isEmpty()) {
//            showError(passwordError, "Обязательное поле");
//            isValid = false;
//        } else if (password.length() < 6) {
//            showError(passwordError, "Минимум 6 символов");
//            isValid = false;
//        }
//
//        return isValid;
//    }
//
//    private void saveCredentials(String username, String password) {
//        try {
//            java.util.Properties props = new java.util.Properties();
//            props.setProperty("username", username);
//            props.setProperty("password", password);
//            props.setProperty("remember", "true");
//
//            try (java.io.FileOutputStream fos = new java.io.FileOutputStream("todo_config.properties")) {
//                props.store(fos, "To-Do App Settings");
//                System.out.println("Данные сохранены для пользователя: " + username);
//            }
//        } catch (Exception e) {
//            System.err.println("Ошибка сохранения данных: " + e.getMessage());
//        }
//    }
//
//    private void clearSavedCredentials() {
//        try {
//            java.io.File configFile = new java.io.File("todo_config.properties");
//            if (configFile.exists()) {
//                configFile.delete();
//                System.out.println("Сохраненные данные удалены");
//            }
//        } catch (Exception e) {
//            System.err.println("Ошибка удаления данных: " + e.getMessage());
//        }
//    }
//
//    private void loadSavedCredentials() {
//        try {
//            java.io.File configFile = new java.io.File("todo_config.properties");
//            if (configFile.exists()) {
//                java.util.Properties props = new java.util.Properties();
//                try (java.io.FileInputStream fis = new java.io.FileInputStream(configFile)) {
//                    props.load(fis);
//
//                    String savedUsername = props.getProperty("username");
//                    String savedPassword = props.getProperty("password");
//                    String remember = props.getProperty("remember", "false");
//
//                    if (savedUsername != null && savedPassword != null) {
//                        usernameField.setText(savedUsername);
//                        passwordField.setText(savedPassword);
//                        rememberCheckBox.setSelected("true".equals(remember));
//
//                        System.out.println("Загружены сохраненные данные для: " + savedUsername);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Ошибка загрузки данных: " + e.getMessage());
//        }
//    }
//
//    private void showMainWindow() {
//        try {
//            // Загружаем главное окно
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/MainWindow.fxml"));
//            Parent root = loader.load();
//
//            // Получаем текущее окно
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//
//            // Получаем контроллер главного окна
//            MainWindowController controller = loader.getController();
//            if (mainApp != null) {
//                controller.setMainApp(mainApp);
//            }
//
//            // Настраиваем сцену
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setTitle("To-Do List - " + (mainApp != null ? mainApp.getCurrentUsername() : "Пользователь"));
//            stage.setWidth(900);
//            stage.setHeight(600);
//            stage.centerOnScreen();
//
//            System.out.println("Переход на главное окно для пользователя: " +
//                    (mainApp != null ? mainApp.getCurrentUsername() : "неизвестно"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showMessage("Ошибка загрузки главного окна: " + e.getMessage(), "red");
//        }
//    }
//
//    private void showError(Label errorLabel, String message) {
//        errorLabel.setText(message);
//        errorLabel.setVisible(true);
//    }
//
//    private void hideError(Label errorLabel) {
//        errorLabel.setVisible(false);
//    }
//
//    private void showMessage(String text, String color) {
//        messageLabel.setText(text);
//        messageLabel.setStyle("-fx-text-fill: " + color + ";");
//        messageLabel.setVisible(true);
//    }
//
//    public void setMainApp(MainApp mainApp) {
//        this.mainApp = mainApp;
//    }
//}

package com.todo.views;

import com.todo.MainApp;
import com.todo.utils.HttpClientUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        // Скрываем сообщение об ошибке
        if (messageLabel != null) {
            messageLabel.setVisible(false);
        }

        // Обработчик Enter в полях
        if (usernameField != null) {
            usernameField.setOnAction(e -> handleLogin());
        }
        if (passwordField != null) {
            passwordField.setOnAction(e -> handleLogin());
        }

        // Загрузка сохраненных данных, если есть
        loadSavedCredentials();
    }

    @FXML
    private void handleLogin() {
        // Сбрасываем сообщения
        if (messageLabel != null) {
            messageLabel.setVisible(false);
        }

        // Проверяем форму
        if (!validateForm()) {
            return;
        }

        // Получаем данные из полей
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Отправляем запрос на сервер
        try {
            // Создаем JSON объект для входа
            Map<String, Object> loginData = new HashMap<>();
            loginData.put("username", username);
            loginData.put("password", password);

            System.out.println("Отправка запроса на вход для пользователя: " + username);

            // Отправляем POST запрос
            Map<String, Object> response = HttpClientUtil.post(
                    "http://localhost:8080/api/auth/login",
                    loginData
            );

            System.out.println("Ответ сервера: " + response);

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                showMessage("Вход выполнен успешно!", "#10b981"); // зеленый

                // Сохраняем данные если отмечено
                if (rememberCheckBox != null && rememberCheckBox.isSelected()) {
                    saveCredentials(username, password);
                } else {
                    clearSavedCredentials();
                }

                // Получаем данные пользователя из ответа
                Map<String, Object> userData = (Map<String, Object>) response.get("user");
                Long userId = userData != null && userData.get("id") != null ?
                        ((Number) userData.get("id")).longValue() : null;
                String userEmail = userData != null ? (String) userData.get("email") : null;


                // Сохраняем данные пользователя в MainApp
                if (mainApp != null) {
                    mainApp.setCurrentUser(username, userId, userEmail);
                    System.out.println(username + userId + userEmail);

                }

                // Задержка перед переходом на главное окно
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        javafx.application.Platform.runLater(this::showMainWindow);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } else {
                String errorMsg = (String) response.getOrDefault("message", "Ошибка входа");
                showMessage("Ошибка: " + errorMsg, "#ef4444"); // красный
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка сети: " + e.getMessage());
            showMessage("Ошибка соединения с сервером: " + e.getMessage(), "#ef4444");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/RegisterView.fxml"));
            Parent root = loader.load();

            // Получаем текущее окно
            Stage stage = (Stage) registerButton.getScene().getWindow();

            // Настраиваем контроллер
            RegisterController controller = loader.getController();
            controller.setMainApp(mainApp);

            // Показываем окно регистрации
            stage.setScene(new Scene(root));
            stage.setTitle("Регистрация");

        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Ошибка загрузки формы: " + e.getMessage(), "#ef4444");
        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Проверка имени пользователя
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showMessage("Имя пользователя обязательно", "#ef4444");
            isValid = false;
        } else if (username.length() < 3) {
            showMessage("Имя пользователя должно быть не менее 3 символов", "#ef4444");
            isValid = false;
        }

        // Проверка пароля
        String password = passwordField.getText();
        if (password.isEmpty()) {
            showMessage("Пароль обязателен", "#ef4444");
            isValid = false;
        } else if (password.length() < 6) {
            showMessage("Пароль должен быть не менее 6 символов", "#ef4444");
            isValid = false;
        }

        return isValid;
    }

    private void saveCredentials(String username, String password) {
        try {
            java.util.Properties props = new java.util.Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("remember", "true");

            try (java.io.FileOutputStream fos = new java.io.FileOutputStream("todo_config.properties")) {
                props.store(fos, "To-Do App Settings");
                System.out.println("Данные сохранены для пользователя: " + username);
            }
        } catch (Exception e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    private void clearSavedCredentials() {
        try {
            java.io.File configFile = new java.io.File("todo_config.properties");
            if (configFile.exists()) {
                configFile.delete();
                System.out.println("Сохраненные данные удалены");
            }
        } catch (Exception e) {
            System.err.println("Ошибка удаления данных: " + e.getMessage());
        }
    }

    private void loadSavedCredentials() {
        try {
            java.io.File configFile = new java.io.File("todo_config.properties");
            if (configFile.exists()) {
                java.util.Properties props = new java.util.Properties();
                try (java.io.FileInputStream fis = new java.io.FileInputStream(configFile)) {
                    props.load(fis);

                    String savedUsername = props.getProperty("username");
                    String savedPassword = props.getProperty("password");
                    String remember = props.getProperty("remember", "false");

                    if (savedUsername != null && savedPassword != null &&
                            usernameField != null && passwordField != null && rememberCheckBox != null) {
                        usernameField.setText(savedUsername);
                        passwordField.setText(savedPassword);
                        rememberCheckBox.setSelected("true".equals(remember));

                        System.out.println("Загружены сохраненные данные для: " + savedUsername);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }

    private void showMainWindow() {
        try {
            // Используем метод MainApp для показа главного окна
            if (mainApp != null) {
                mainApp.showMainApp();
            } else {
                // Fallback: загружаем напрямую
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/MainWindow.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();

                MainWindowController controller = loader.getController();
                controller.setMainApp(mainApp);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("To-Do List");
                stage.setWidth(900);
                stage.setHeight(600);
                stage.centerOnScreen();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Ошибка загрузки главного окна: " + e.getMessage(), "#ef4444");
        }
    }

    private void showMessage(String text, String color) {
        if (messageLabel != null) {
            messageLabel.setText(text);
            messageLabel.setStyle("-fx-text-fill: " + color + ";");
            messageLabel.setVisible(true);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}