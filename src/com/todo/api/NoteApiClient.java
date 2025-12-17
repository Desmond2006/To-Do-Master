package com.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.models.Note;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/notes";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.findAndRegisterModules();
    }

    /**
     * Получить заметки пользователя с сервера
     */
    public static List<Note> loadUserNotes(Long userId) {
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
                    List<Map<String, Object>> notesData = (List<Map<String, Object>>) responseBody.get("notes");
                    List<Note> notes = new ArrayList<>();

                    for (Map<String, Object> noteData : notesData) {
                        Note note = convertToNote(noteData);
                        notes.add(note);
                    }

                    return notes;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Создать заметку на сервере
     */
    public static boolean createNote(Note note, Long userId) {
        try {
            String requestBody = mapper.writeValueAsString(createNoteRequest(note));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?userId=" + userId))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);

                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    Map<String, Object> noteData = (Map<String, Object>) responseBody.get("note");
                    if (noteData != null && noteData.get("id") != null) {
                        note.setId(((Number) noteData.get("id")).longValue());
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
     * Обновить заметку на сервере
     */
    public static boolean updateNote(Note note, Long userId) {
        try {
            String requestBody = mapper.writeValueAsString(createNoteRequest(note));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + note.getId() + "?userId=" + userId))
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
     * Удалить заметку с сервера
     */
    public static boolean deleteNote(Long noteId, Long userId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + noteId + "?userId=" + userId))
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

    /**
     * Поиск заметок
     */
    public static List<Note> searchNotes(Long userId, String query) {
        try {
            String url = BASE_URL + "/search?userId=" + userId + "&query=" + query;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);

                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    List<Map<String, Object>> notesData = (List<Map<String, Object>>) responseBody.get("notes");
                    List<Note> notes = new ArrayList<>();

                    for (Map<String, Object> noteData : notesData) {
                        Note note = convertToNote(noteData);
                        notes.add(note);
                    }

                    return notes;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static Map<String, Object> createNoteRequest(Note note) {
        return Map.of(
                "title", note.getTitle(),
                "content", note.getContent()
        );
    }

    private static Note convertToNote(Map<String, Object> noteData) {
        Note note = new Note();
        note.setId(((Number) noteData.get("id")).longValue());
        note.setTitle((String) noteData.get("title"));
        note.setContent((String) noteData.get("content"));

        if (noteData.get("createdAt") != null) {
            String createdAtStr = (String) noteData.get("createdAt");
            note.setCreatedDate(LocalDateTime.parse(createdAtStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        if (noteData.get("updatedAt") != null) {
            String updatedAtStr = (String) noteData.get("updatedAt");
            note.setModifiedDate(LocalDateTime.parse(updatedAtStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        return note;
    }
}