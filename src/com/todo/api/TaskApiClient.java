package com.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.models.Task;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/tasks";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    // Настройка ObjectMapper для работы с LocalDate
    static {
        mapper.findAndRegisterModules();
    }

    /**
     * Получить задачи пользователя с сервера
     */
    public static List<Task> loadUserTasks(Long userId) {
        try {
            String url = BASE_URL + "?userId=" + userId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);

                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    List<Map<String, Object>> tasksData = (List<Map<String, Object>>) responseBody.get("tasks");
                    List<Task> tasks = new ArrayList<>();

                    for (Map<String, Object> taskData : tasksData) {
                        Task task = convertToTask(taskData);
                        tasks.add(task);
                    }

                    return tasks;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Создать задачу на сервере
     */
    public static boolean createTask(Task task, Long userId) {
        try {
            String requestBody = mapper.writeValueAsString(createTaskRequest(task));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?userId=" + userId))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);

                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    // Обновляем ID задачи с сервера
                    Map<String, Object> taskData = (Map<String, Object>) responseBody.get("task");
                    if (taskData != null && taskData.get("id") != null) {
                        task.setId(((Number) taskData.get("id")).longValue());
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Обновить задачу на сервере
     */
    public static boolean updateTask(Task task, Long userId) {
        try {
            String requestBody = mapper.writeValueAsString(createTaskRequest(task));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + task.getId() + "?userId=" + userId))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);
                return Boolean.TRUE.equals(responseBody.get("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Удалить задачу с сервера
     */
    public static boolean deleteTask(Long taskId, Long userId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + taskId + "?userId=" + userId))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);
                return Boolean.TRUE.equals(responseBody.get("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Map<String, Object> createTaskRequest(Task task) {
        return Map.of(
                "taskText", task.getTaskText(),
                "deadline", task.getDeadline() != null ?
                        task.getDeadline().format(DateTimeFormatter.ISO_DATE) : null,
                "completed", task.getCompleted()
        );
    }

    private static Task convertToTask(Map<String, Object> taskData) {
        Task task = new Task();
        task.setId(((Number) taskData.get("id")).longValue());
        task.setTaskText((String) taskData.get("taskText"));
        task.setCompleted(Boolean.TRUE.equals(taskData.get("completed")));

        if (taskData.get("deadline") != null) {
            String deadlineStr = (String) taskData.get("deadline");
            task.setDeadline(LocalDate.parse(deadlineStr, DateTimeFormatter.ISO_DATE));
        }

        return task;
    }
}