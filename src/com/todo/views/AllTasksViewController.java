package com.todo.views;

import com.todo.MainApp;
import com.todo.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AllTasksViewController implements Initializable {

    @FXML
    private VBox tasksContainer;

    @FXML
    private Label tasksCountLabel;

    @FXML
    private Label completedCountLabel;

    @FXML
    private VBox addTaskBox;

    @FXML
    private TextField newTaskField;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnSaveTask;

    @FXML
    private Button btnCancelTask;

    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFilterComboBox();
        setupSearchField();
        setupEventHandlers();
        setupDatePicker();
    }

    /**
     * Устанавливает ссылку на главное приложение
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        updateTasksDisplay();
    }

    private void setupFilterComboBox() {
        filterComboBox.getItems().addAll(
                "Все задачи",
                "Активные",
                "Выполненные",
                "На сегодня",
                "Просроченные"
        );
        filterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTasksDisplay());

        // Устанавливаем значение по умолчанию
        filterComboBox.setValue("Все задачи");
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateTasksDisplay());
    }

    private void setupDatePicker() {
        // Устанавливаем дату по умолчанию - через 7 дней
        deadlinePicker.setValue(LocalDate.now().plusDays(7));

        // Настраиваем отображение даты
        deadlinePicker.setShowWeekNumbers(false);

        // Устанавливаем минимальную дату - сегодня
        deadlinePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }

    private void setupEventHandlers() {
        // Обработка нажатия Enter в поле поиска
        searchField.setOnAction(e -> updateTasksDisplay());

        // Обработка нажатия Enter в поле новой задачи
        newTaskField.setOnAction(e -> saveTask());
    }

    private void updateTasksDisplay() {
        if (mainApp == null) return;

        tasksContainer.getChildren().clear();

        // Фильтруем и отображаем задачи
        for (Task task : mainApp.getTaskData()) {
            if (shouldDisplayTask(task)) {
                createTaskCard(task);
            }
        }

        updateStatistics();
    }

    private boolean shouldDisplayTask(Task task) {
        String filter = filterComboBox.getValue();
        String searchText = searchField.getText().toLowerCase();

        // Проверка поиска
        boolean matchesSearch = searchText.isEmpty() ||
                task.getTaskText().toLowerCase().contains(searchText);

        if (!matchesSearch) return false;

        // Проверка фильтра
        if (filter == null || "Все задачи".equals(filter)) {
            return true;
        }

        switch (filter) {
            case "Активные":
                return !task.getCompleted();
            case "Выполненные":
                return task.getCompleted();
            case "На сегодня":
                return task.getDeadline() != null &&
                        task.getDeadline().equals(LocalDate.now());
            case "Просроченные":
                return task.getDeadline() != null &&
                        task.getDeadline().isBefore(LocalDate.now()) &&
                        !task.getCompleted();
            default:
                return true;
        }
    }

    private void createTaskCard(Task task) {
        HBox taskCard = new HBox();
        taskCard.setSpacing(12);
        taskCard.setPadding(new Insets(12));
        taskCard.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");

        // Checkbox для отметки выполнения
        CheckBox completionCheckbox = new CheckBox();
        completionCheckbox.setSelected(task.getCompleted());
        completionCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            task.setCompleted(newVal);
            updateTaskAppearance(taskCard, task);
            updateStatistics();
        });

        // Основная информация о задаче
        VBox taskInfo = new VBox();
        taskInfo.setSpacing(4);
        taskInfo.setPrefWidth(300);

        Label titleLabel = new Label(task.getTaskText());
        titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        titleLabel.setWrapText(true);

        HBox detailsBox = new HBox();
        detailsBox.setSpacing(10);
        detailsBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Отображаем дату выполнения
        if (task.getDeadline() != null) {
            String dateStyle = task.getDeadline().isBefore(LocalDate.now()) && !task.getCompleted()
                    ? "-fx-text-fill: #ef4444; -fx-font-weight: bold;"
                    : "-fx-text-fill: #64748b;";

            Label dateLabel = new Label("📅 " + task.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            dateLabel.setStyle("-fx-font-size: 12; " + dateStyle);
            detailsBox.getChildren().add(dateLabel);

            // Кнопка для изменения даты
            Button editDateButton = new Button("✏️");
            editDateButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3b82f6; -fx-font-size: 10;");
            editDateButton.setTooltip(new Tooltip("Изменить дату"));
            editDateButton.setOnAction(e -> editTaskDate(task));
            detailsBox.getChildren().add(editDateButton);
        } else {
            // Если даты нет - кнопка для добавления
            Button addDateButton = new Button("➕ Добавить дату");
            addDateButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3b82f6; -fx-font-size: 10;");
            addDateButton.setOnAction(e -> editTaskDate(task));
            detailsBox.getChildren().add(addDateButton);
        }

        // Отображаем статус
        Label statusLabel = new Label(task.getCompleted() ? "✅ Выполнено" : "⏳ Активно");
        statusLabel.setStyle("-fx-font-size: 12; -fx-text-fill: " + (task.getCompleted() ? "#10b981" : "#f59e0b") + ";");
        detailsBox.getChildren().add(statusLabel);

        taskInfo.getChildren().addAll(titleLabel, detailsBox);

        // Растягивающийся регион
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Кнопка удаления
        Button deleteButton = new Button("🗑️");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-size: 14;");
        deleteButton.setOnAction(e -> deleteTask(task));
        deleteButton.setTooltip(new Tooltip("Удалить задачу"));

        taskCard.getChildren().addAll(completionCheckbox, taskInfo, spacer, deleteButton);

        updateTaskAppearance(taskCard, task);
        tasksContainer.getChildren().add(taskCard);
    }

    private void editTaskDate(Task task) {
        // Создаем диалог для изменения даты
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Изменение даты выполнения");
        dialog.setHeaderText("Выберите новую дату для задачи:\n\"" + task.getTaskText() + "\"");

        // Устанавливаем кнопки
        ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Создаем DatePicker
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(task.getDeadline() != null ? task.getDeadline() : LocalDate.now().plusDays(7));
        datePicker.setShowWeekNumbers(false);

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.getChildren().addAll(new Label("Дата выполнения:"), datePicker);

        dialog.getDialogPane().setContent(content);

        // Преобразуем результат
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return datePicker.getValue();
            }
            return null;
        });

        // Обрабатываем результат
        dialog.showAndWait().ifPresent(newDate -> {
            task.setDeadline(newDate);
            updateTasksDisplay();
        });
    }

    private void updateTaskAppearance(HBox taskCard, Task task) {
        String style = task.getCompleted()
                ? "-fx-background-color: #f0fdf4; -fx-border-color: #bbf7d0; " +
                "-fx-opacity: 0.8;"
                : "-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0;";

        taskCard.setStyle(style + " -fx-background-radius: 8; -fx-border-radius: 8;");

        // Зачеркиваем текст для выполненных задач
        Label titleLabel = (Label) ((VBox) taskCard.getChildren().get(1)).getChildren().get(0);
        if (task.getCompleted()) {
            titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-strikethrough: true; -fx-text-fill: #64748b;");
        } else {
            titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        }
    }

    private void updateStatistics() {
        if (mainApp == null) return;

        long totalTasks = mainApp.getTaskData().size();
        long completedTasks = mainApp.getTaskData().stream()
                .filter(Task::getCompleted)
                .count();
        long activeTasks = totalTasks - completedTasks;

        tasksCountLabel.setText(activeTasks + " активных, " + completedTasks + " выполнено");
        completedCountLabel.setText("Выполнено: " + completedTasks + " из " + totalTasks);
    }

    @FXML
    private void addNewTask() {
        addTaskBox.setVisible(true);
        newTaskField.clear();
        deadlinePicker.setValue(LocalDate.now().plusDays(7)); // Дата по умолчанию
        newTaskField.requestFocus();
    }

    @FXML
    private void saveTask() {
        String taskText = newTaskField.getText().trim();
        LocalDate deadline = deadlinePicker.getValue();

        if (!taskText.isEmpty()) {
            Task newTask = new Task(taskText, deadline);
//            mainApp.getTaskData().add(newTask);
            mainApp.addTask(newTask);
            cancelAddTask();
            updateTasksDisplay();
        } else {
            showAlert("Ошибка", "Введите текст задачи");
        }
    }

    @FXML
    private void cancelAddTask() {
        addTaskBox.setVisible(false);
        newTaskField.clear();
    }

    private void deleteTask(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление задачи");
        alert.setHeaderText("Удалить задачу?");
        alert.setContentText("Задача \"" + task.getTaskText() + "\" будет удалена безвозвратно.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
//                mainApp.getTaskData().remove(task);
                mainApp.deleteTask(task);
                updateTasksDisplay();
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод для обновления отображения при внешних изменениях
     */
    public void refresh() {
        updateTasksDisplay();
    }
}