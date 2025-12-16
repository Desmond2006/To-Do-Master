// client/src/main/java/com/todo/controllers/RegisterController.java
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
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label usernameError;

    @FXML
    private Label emailError;

    @FXML
    private Label passwordError;

    @FXML
    private Label confirmError;

    @FXML
    private Label messageLabel;

    private MainApp mainApp;

    // Простые проверки
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @FXML
    private void initialize() {
        // Скрываем все сообщения об ошибках
        usernameError.setVisible(false);
        emailError.setVisible(false);
        passwordError.setVisible(false);
        confirmError.setVisible(false);
        messageLabel.setVisible(false);

        // Валидация в реальном времени
        setupValidation();
    }

    private void setupValidation() {
        // Проверка имени пользователя
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() < 3) {
                showError(usernameError, "Минимум 3 символа");
            } else if (!USERNAME_PATTERN.matcher(newVal).matches()) {
                showError(usernameError, "Только буквы, цифры и _");
            } else {
                hideError(usernameError);
            }
        });

        // Проверка email (не обязателен)
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && !EMAIL_PATTERN.matcher(newVal).matches()) {
                showError(emailError, "Некорректный email");
            } else {
                hideError(emailError);
            }
        });

        // Проверка пароля
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() < 6) {
                showError(passwordError, "Минимум 6 символов");
            } else {
                hideError(passwordError);
            }

            // Проверяем совпадение паролей
            if (!confirmPasswordField.getText().isEmpty()) {
                checkPasswordsMatch();
            }
        });

        // Проверка подтверждения пароля
        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            checkPasswordsMatch();
        });
    }

    private void checkPasswordsMatch() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showError(confirmError, "Пароли не совпадают");
        } else {
            hideError(confirmError);
        }
    }

    @FXML
    private void handleRegister() {
        // Сбрасываем сообщения
        messageLabel.setVisible(false);

        // Проверяем форму
        if (!validateForm()) {
            return;
        }

        // Создаем пользователя
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String email = emailField.getText().trim();

        // Отправляем запрос на сервер
        try {
            // Создаем JSON объект для регистрации
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email.isEmpty() ? null : email);
            userData.put("password", password); // Сервер сам захеширует

            // Отправляем POST запрос
            Map<String, Object> response = HttpClientUtil.post(
                    "http://localhost:8080/api/auth/register",
                    userData
            );

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                showMessage("Регистрация успешна! Теперь вы можете войти.", "green");

                // Чистим форму
                clearForm();

                // Автоматически переходим на форму входа через 2 секунды
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(this::handleLogin);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                String errorMsg = (String) response.getOrDefault("message", "Ошибка регистрации");
                showMessage("Ошибка: " + errorMsg, "red");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Ошибка соединения с сервером", "red");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todo/views/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();

            LoginController controller = loader.getController();
            controller.setMainApp(mainApp);

            stage.setScene(new Scene(root));
            stage.setTitle("Вход");

        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Ошибка: " + e.getMessage(), "red");
        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Проверка имени пользователя
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showError(usernameError, "Обязательное поле");
            isValid = false;
        } else if (!USERNAME_PATTERN.matcher(username).matches()) {
            showError(usernameError, "Некорректное имя");
            isValid = false;
        }

        // Проверка email
        String email = emailField.getText().trim();
        if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            showError(emailError, "Некорректный email");
            isValid = false;
        }

        // Проверка пароля
        String password = passwordField.getText();
        if (password.length() < 6) {
            showError(passwordError, "Минимум 6 символов");
            isValid = false;
        }

        // Проверка подтверждения
        if (!password.equals(confirmPasswordField.getText())) {
            showError(confirmError, "Пароли не совпадают");
            isValid = false;
        }

        return isValid;
    }

    private void showError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void hideError(Label errorLabel) {
        errorLabel.setVisible(false);
    }

    private void showMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
        messageLabel.setVisible(true);
    }

    private void clearForm() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();

        hideError(usernameError);
        hideError(emailError);
        hideError(passwordError);
        hideError(confirmError);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}