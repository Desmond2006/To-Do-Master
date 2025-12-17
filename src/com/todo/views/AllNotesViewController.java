//package com.todo.views;
//
//import com.todo.MainApp;
//import com.todo.models.Note;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ResourceBundle;
//
//public class AllNotesViewController implements Initializable {
//
//    @FXML
//    private VBox notesContainer;
//
//    @FXML
//    private Label notesCountLabel;
//
//    @FXML
//    private Label lastModifiedLabel;
//
//    @FXML
//    private VBox addNoteBox;
//
//    @FXML
//    private TextField newNoteTitleField;
//
//    @FXML
//    private TextArea newNoteContentField;
//
//    @FXML
//    private ComboBox<String> filterComboBox;
//
//    @FXML
//    private TextField searchField;
//
//    @FXML
//    private Button btnAddNote;
//
//    @FXML
//    private Button btnSaveNote;
//
//    @FXML
//    private Button btnCancelNote;
//
//    private MainApp mainApp;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        setupFilterComboBox();
//        setupSearchField();
//        setupEventHandlers();
//        setupDefaultTitle();
//    }
//
//    /**
//     * Устанавливает ссылку на главное приложение
//     */
//    public void setMainApp(MainApp mainApp) {
//        this.mainApp = mainApp;
//        updateNotesDisplay();
//    }
//
//    private void setupFilterComboBox() {
//        filterComboBox.getItems().addAll(
//                "Все заметки",
//                "Сегодня",
//                "За последнюю неделю",
//                "За последний месяц"
//        );
//        filterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateNotesDisplay());
//
//        // Устанавливаем значение по умолчанию
//        filterComboBox.setValue("Все заметки");
//    }
//
//    private void setupSearchField() {
//        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateNotesDisplay());
//    }
//
//    private void setupEventHandlers() {
//        // Обработка нажатия Enter в поле поиска
//        searchField.setOnAction(e -> updateNotesDisplay());
//
//        // Обработка нажатия Enter в поле заголовка
//        newNoteTitleField.setOnAction(e -> newNoteContentField.requestFocus());
//    }
//
//    private void setupDefaultTitle() {
//        // Устанавливаем текущую дату как заголовок по умолчанию
//        String defaultTitle = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//        newNoteTitleField.setText(defaultTitle);
//    }
//
//    private void updateNotesDisplay() {
//        if (mainApp == null) return;
//
//        notesContainer.getChildren().clear();
//
//        // Фильтруем и отображаем заметки
//        for (Note note : mainApp.getNoteData()) {
//            if (shouldDisplayNote(note)) {
//                createNoteCard(note);
//            }
//        }
//
//        updateStatistics();
//    }
//
//    private boolean shouldDisplayNote(Note note) {
//        String filter = filterComboBox.getValue();
//        String searchText = searchField.getText().toLowerCase();
//
//        // Проверка поиска
//        boolean matchesSearch = searchText.isEmpty() ||
//                note.getTitle().toLowerCase().contains(searchText) ||
//                note.getContent().toLowerCase().contains(searchText);
//
//        if (!matchesSearch) return false;
//
//        // Проверка фильтра
//        if (filter == null || "Все заметки".equals(filter)) {
//            return true;
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        switch (filter) {
//            case "Сегодня":
//                return note.getCreatedDate().toLocalDate().equals(now.toLocalDate());
//            case "За последнюю неделю":
//                return note.getCreatedDate().isAfter(now.minusWeeks(1));
//            case "За последний месяц":
//                return note.getCreatedDate().isAfter(now.minusMonths(1));
//            default:
//                return true;
//        }
//    }
//
//    private void createNoteCard(Note note) {
//        VBox noteCard = new VBox();
//        noteCard.setSpacing(8);
//        noteCard.setPadding(new Insets(15));
//        noteCard.setStyle("-fx-background-color: #fefce8; -fx-background-radius: 8; -fx-border-color: #fef08a; -fx-border-radius: 8; -fx-border-width: 1;");
//
//        // Заголовок и дата
//        HBox headerBox = new HBox();
//        headerBox.setSpacing(10);
//        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
//
//        Label titleLabel = new Label(note.getTitle());
//        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #854d0e;");
//        titleLabel.setWrapText(true);
//
//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//
//        Label dateLabel = new Label("📅 " + note.getFormattedCreatedDate());
//        dateLabel.setStyle("-fx-text-fill: #a16207; -fx-font-size: 11;");
//
//        headerBox.getChildren().addAll(titleLabel, spacer, dateLabel);
//
//        // Превью содержимого
//        Label contentPreview = new Label(note.getPreview());
//        contentPreview.setStyle("-fx-text-fill: #57534e; -fx-font-size: 13;");
//        contentPreview.setWrapText(true);
//        contentPreview.setMaxWidth(Double.MAX_VALUE);
//
//        // Информация о изменении
//        Label modifiedLabel = new Label("✏️ Изменено: " + note.getFormattedModifiedDate());
//        modifiedLabel.setStyle("-fx-text-fill: #78716c; -fx-font-size: 10;");
//
//        // Кнопки действий
//        HBox actionsBox = new HBox();
//        actionsBox.setSpacing(8);
//        actionsBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
//
//        Button editButton = new Button("✏️ Редактировать");
//        editButton.setStyle("-fx-background-color: #fbbf24; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
//        editButton.setOnAction(e -> editNote(note));
//
//        Button deleteButton = new Button("🗑️ Удалить");
//        deleteButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
//        deleteButton.setOnAction(e -> deleteNote(note));
//
//        Button viewButton = new Button("👁️ Просмотреть");
//        viewButton.setStyle("-fx-background-color: #8b5cf6; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
//        viewButton.setOnAction(e -> viewNote(note));
//
//        actionsBox.getChildren().addAll(viewButton, editButton, deleteButton);
//
//        noteCard.getChildren().addAll(headerBox, contentPreview, modifiedLabel, actionsBox);
//        notesContainer.getChildren().add(noteCard);
//    }
//
//    private void viewNote(Note note) {
//        // Диалог для просмотра полной заметки
//        Dialog<Void> dialog = new Dialog<>();
//        dialog.setTitle("Просмотр заметки");
//        dialog.setHeaderText(note.getTitle());
//
//        ButtonType closeButtonType = new ButtonType("Закрыть", ButtonBar.ButtonData.CANCEL_CLOSE);
//        dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);
//
//        VBox content = new VBox();
//        content.setSpacing(10);
//        content.setPadding(new Insets(10));
//
//        Label dateLabel = new Label("Создано: " + note.getFormattedCreatedDate() +
//                "\nИзменено: " + note.getFormattedModifiedDate());
//        dateLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12;");
//
//        TextArea contentArea = new TextArea(note.getContent());
//        contentArea.setEditable(false);
//        contentArea.setWrapText(true);
//        contentArea.setPrefRowCount(15);
//        contentArea.setStyle("-fx-font-size: 14; -fx-background-color: #f8fafc;");
//
//        content.getChildren().addAll(dateLabel, contentArea);
//        dialog.getDialogPane().setContent(content);
//
//        dialog.showAndWait();
//    }
//
//    private void editNote(Note note) {
//        // Диалог для редактирования заметки
//        Dialog<Note> dialog = new Dialog<>();
//        dialog.setTitle("Редактирование заметки");
//        dialog.setHeaderText("Редактирование заметки");
//
//        ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
//
//        VBox content = new VBox();
//        content.setSpacing(10);
//        content.setPadding(new Insets(10));
//
//        TextField titleField = new TextField(note.getTitle());
//        titleField.setPromptText("Заголовок заметки");
//        titleField.setStyle("-fx-background-radius: 6; -fx-padding: 8;");
//
//        TextArea contentArea = new TextArea(note.getContent());
//        contentArea.setPromptText("Текст заметки");
//        contentArea.setWrapText(true);
//        contentArea.setPrefRowCount(10);
//        contentArea.setStyle("-fx-background-radius: 6; -fx-padding: 8;");
//
//        content.getChildren().addAll(
//                new Label("Заголовок:"), titleField,
//                new Label("Текст заметки:"), contentArea
//        );
//
//        dialog.getDialogPane().setContent(content);
//
//        // Преобразуем результат
//        dialog.setResultConverter(dialogButton -> {
//            if (dialogButton == saveButtonType) {
//                note.setTitle(titleField.getText());
//                note.setContent(contentArea.getText());
//                return note;
//            }
//            return null;
//        });
//
//        dialog.showAndWait().ifPresent(updatedNote -> {
//            updateNotesDisplay();
//        });
//    }
//
//    private void updateStatistics() {
//        if (mainApp == null) return;
//
//        int totalNotes = mainApp.getNoteData().size();
//        notesCountLabel.setText(totalNotes + " заметок");
//
//        // Показываем дату последнего изменения
//        if (totalNotes > 0) {
//            LocalDateTime lastModified = mainApp.getNoteData().stream()
//                    .map(Note::getModifiedDate)
//                    .max(LocalDateTime::compareTo)
//                    .orElse(LocalDateTime.now());
//
//            String formattedDate = lastModified.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//            lastModifiedLabel.setText("Последнее изменение: " + formattedDate);
//        } else {
//            lastModifiedLabel.setText("");
//        }
//    }
//
//    @FXML
//    private void addNewNote() {
//        addNoteBox.setVisible(true);
//        setupDefaultTitle(); // Устанавливаем текущую дату как заголовок по умолчанию
//        newNoteContentField.clear();
//        newNoteTitleField.requestFocus();
//    }
//
//    @FXML
//    private void saveNote() {
//        String title = newNoteTitleField.getText().trim();
//        String content = newNoteContentField.getText().trim();
//
//        if (!title.isEmpty()) {
//            Note newNote = new Note(title, content);
//            mainApp.getNoteData().add(newNote);
//            cancelAddNote();
//            updateNotesDisplay();
//        } else {
//            showAlert("Ошибка", "Введите заголовок заметки");
//        }
//    }
//
//    @FXML
//    private void cancelAddNote() {
//        addNoteBox.setVisible(false);
//        newNoteTitleField.clear();
//        newNoteContentField.clear();
//        setupDefaultTitle(); // Восстанавливаем заголовок по умолчанию
//    }
//
//    private void deleteNote(Note note) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Удаление заметки");
//        alert.setHeaderText("Удалить заметку?");
//        alert.setContentText("Заметка \"" + note.getTitle() + "\" будет удалена безвозвратно.");
//
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                mainApp.getNoteData().remove(note);
//                updateNotesDisplay();
//            }
//        });
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    /**
//     * Метод для обновления отображения при внешних изменениях
//     */
//    public void refresh() {
//        updateNotesDisplay();
//    }
//}
package com.todo.views;

import com.todo.MainApp;
import com.todo.models.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AllNotesViewController implements Initializable {

    @FXML
    private VBox notesContainer;

    @FXML
    private Label notesCountLabel;

    @FXML
    private Label lastModifiedLabel;

    @FXML
    private VBox addNoteBox;

    @FXML
    private TextField newNoteTitleField;

    @FXML
    private TextArea newNoteContentField;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnAddNote;

    @FXML
    private Button btnSaveNote;

    @FXML
    private Button btnCancelNote;

    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFilterComboBox();
        setupSearchField();
        setupEventHandlers();
        setupDefaultTitle();
    }

    /**
     * Устанавливает ссылку на главное приложение
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        updateNotesDisplay();
    }

    private void setupFilterComboBox() {
        filterComboBox.getItems().addAll(
                "Все заметки",
                "Сегодня",
                "За последнюю неделю",
                "За последний месяц"
        );
        filterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateNotesDisplay());

        // Устанавливаем значение по умолчанию
        filterComboBox.setValue("Все заметки");
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateNotesDisplay());
    }

    private void setupEventHandlers() {
        // Обработка нажатия Enter в поле поиска
        searchField.setOnAction(e -> updateNotesDisplay());

        // Обработка нажатия Enter в поле заголовка
        newNoteTitleField.setOnAction(e -> newNoteContentField.requestFocus());
    }

    private void setupDefaultTitle() {
        // Устанавливаем текущую дату как заголовок по умолчанию
        String defaultTitle = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        newNoteTitleField.setText(defaultTitle);
    }

    private void updateNotesDisplay() {
        if (mainApp == null) return;

        notesContainer.getChildren().clear();

        // Фильтруем и отображаем заметки
        for (Note note : mainApp.getNoteData()) {
            if (shouldDisplayNote(note)) {
                createNoteCard(note);
            }
        }

        updateStatistics();
    }

    private boolean shouldDisplayNote(Note note) {
        String filter = filterComboBox.getValue();
        String searchText = searchField.getText().toLowerCase();

        // Проверка поиска
        boolean matchesSearch = searchText.isEmpty() ||
                note.getTitle().toLowerCase().contains(searchText) ||
                note.getContent().toLowerCase().contains(searchText);

        if (!matchesSearch) return false;

        // Проверка фильтра
        if (filter == null || "Все заметки".equals(filter)) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (filter) {
            case "Сегодня":
                return note.getCreatedDate().toLocalDate().equals(now.toLocalDate());
            case "За последнюю неделю":
                return note.getCreatedDate().isAfter(now.minusWeeks(1));
            case "За последний месяц":
                return note.getCreatedDate().isAfter(now.minusMonths(1));
            default:
                return true;
        }
    }

    private void createNoteCard(Note note) {
        VBox noteCard = new VBox();
        noteCard.setSpacing(8);
        noteCard.setPadding(new Insets(15));
        noteCard.setStyle("-fx-background-color: #fefce8; -fx-background-radius: 8; -fx-border-color: #fef08a; -fx-border-radius: 8; -fx-border-width: 1;");

        // Заголовок и дата
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label titleLabel = new Label(note.getTitle());
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #854d0e;");
        titleLabel.setWrapText(true);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dateLabel = new Label("📅 " + note.getFormattedCreatedDate());
        dateLabel.setStyle("-fx-text-fill: #a16207; -fx-font-size: 11;");

        headerBox.getChildren().addAll(titleLabel, spacer, dateLabel);

        // Превью содержимого
        Label contentPreview = new Label(note.getPreview());
        contentPreview.setStyle("-fx-text-fill: #57534e; -fx-font-size: 13;");
        contentPreview.setWrapText(true);
        contentPreview.setMaxWidth(Double.MAX_VALUE);

        // Информация о изменении
        Label modifiedLabel = new Label("✏️ Изменено: " + note.getFormattedModifiedDate());
        modifiedLabel.setStyle("-fx-text-fill: #78716c; -fx-font-size: 10;");

        // Кнопки действий
        HBox actionsBox = new HBox();
        actionsBox.setSpacing(8);
        actionsBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        Button editButton = new Button("✏️ Редактировать");
        editButton.setStyle("-fx-background-color: #fbbf24; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
        editButton.setOnAction(e -> editNote(note));

        Button deleteButton = new Button("🗑️ Удалить");
        deleteButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
        deleteButton.setOnAction(e -> deleteNote(note));

        Button viewButton = new Button("👁️ Просмотреть");
        viewButton.setStyle("-fx-background-color: #8b5cf6; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11; -fx-padding: 4 8;");
        viewButton.setOnAction(e -> viewNote(note));

        actionsBox.getChildren().addAll(viewButton, editButton, deleteButton);

        noteCard.getChildren().addAll(headerBox, contentPreview, modifiedLabel, actionsBox);
        notesContainer.getChildren().add(noteCard);
    }

    private void viewNote(Note note) {
        // Диалог для просмотра полной заметки
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Просмотр заметки");
        dialog.setHeaderText(note.getTitle());

        ButtonType closeButtonType = new ButtonType("Закрыть", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        Label dateLabel = new Label("Создано: " + note.getFormattedCreatedDate() +
                "\nИзменено: " + note.getFormattedModifiedDate());
        dateLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12;");

        TextArea contentArea = new TextArea(note.getContent());
        contentArea.setEditable(false);
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(15);
        contentArea.setStyle("-fx-font-size: 14; -fx-background-color: #f8fafc;");

        content.getChildren().addAll(dateLabel, contentArea);
        dialog.getDialogPane().setContent(content);

        dialog.showAndWait();
    }

    private void editNote(Note note) {
        // Диалог для редактирования заметки
        Dialog<Note> dialog = new Dialog<>();
        dialog.setTitle("Редактирование заметки");
        dialog.setHeaderText("Редактирование заметки");

        ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        TextField titleField = new TextField(note.getTitle());
        titleField.setPromptText("Заголовок заметки");
        titleField.setStyle("-fx-background-radius: 6; -fx-padding: 8;");

        TextArea contentArea = new TextArea(note.getContent());
        contentArea.setPromptText("Текст заметки");
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(10);
        contentArea.setStyle("-fx-background-radius: 6; -fx-padding: 8;");

        content.getChildren().addAll(
                new Label("Заголовок:"), titleField,
                new Label("Текст заметки:"), contentArea
        );

        dialog.getDialogPane().setContent(content);

        // Преобразуем результат
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                note.setTitle(titleField.getText());
                note.setContent(contentArea.getText());
                // Сохраняем изменения на сервер
                mainApp.updateNote(note);
                return note;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedNote -> {
            updateNotesDisplay();
        });
    }

    private void updateStatistics() {
        if (mainApp == null) return;

        int totalNotes = mainApp.getNoteData().size();
        notesCountLabel.setText(totalNotes + " заметок");

        // Показываем дату последнего изменения
        if (totalNotes > 0) {
            LocalDateTime lastModified = mainApp.getNoteData().stream()
                    .map(Note::getModifiedDate)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            String formattedDate = lastModified.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            lastModifiedLabel.setText("Последнее изменение: " + formattedDate);
        } else {
            lastModifiedLabel.setText("");
        }
    }

    @FXML
    private void addNewNote() {
        addNoteBox.setVisible(true);
        setupDefaultTitle(); // Устанавливаем текущую дату как заголовок по умолчанию
        newNoteContentField.clear();
        newNoteTitleField.requestFocus();
    }

    @FXML
    private void saveNote() {
        String title = newNoteTitleField.getText().trim();
        String content = newNoteContentField.getText().trim();

        if (!title.isEmpty()) {
            Note newNote = new Note(title, content);
            mainApp.addNote(newNote); // Изменяем эту строку
            cancelAddNote();
            updateNotesDisplay();
        } else {
            showAlert("Ошибка", "Введите заголовок заметки");
        }
    }

    @FXML
    private void cancelAddNote() {
        addNoteBox.setVisible(false);
        newNoteTitleField.clear();
        newNoteContentField.clear();
        setupDefaultTitle(); // Восстанавливаем заголовок по умолчанию
    }

    private void deleteNote(Note note) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление заметки");
        alert.setHeaderText("Удалить заметку?");
        alert.setContentText("Заметка \"" + note.getTitle() + "\" будет удалена безвозвратно.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mainApp.deleteNote(note); // Изменяем эту строку
                updateNotesDisplay();
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
        updateNotesDisplay();
    }
}