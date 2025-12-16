// client/src/main/java/com/todo/TestServerConnection.java
package com.todo;

import java.io.IOException;
import java.net.Socket;

public class TestServerConnection {

//    public static void main(String[] args) {
//        System.out.println("Проверка подключения к Spring Boot серверу");
//        System.out.println("==========================================\n");
//
//        String host = "localhost";
//        int port = 8080;
//
//        // 1. Проверяем доступность порта
//        System.out.println("1. Проверка порта " + port + "...");
//        boolean isPortOpen = checkPort(host, port);
//
//        if (isPortOpen) {
//            System.out.println("✅ Порт " + port + " открыт на " + host);
//            System.out.println("Сервер вероятно запущен!");
//
//            // 2. Делаем HTTP запрос
//            System.out.println("\n2. Проверка HTTP ответа...");
//            makeHttpRequest();
//        } else {
//            System.out.println("❌ Порт " + port + " закрыт или недоступен");
//            System.out.println("\nЧто делать:");
//            System.out.println("1. Запустите Spring Boot приложение:");
//            System.out.println("   mvn spring-boot:run");
//            System.out.println("2. Или: java -jar target/your-app.jar");
//            System.out.println("3. Проверьте, что порт 8080 не занят другим приложением");
//            System.out.println("4. Проверьте настройки в application.properties");
//        }
//    }
//
//    private static boolean checkPort(String host, int port) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new java.net.InetSocketAddress(host, port), 3000);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }
//
//    private static void makeHttpRequest() {
//        try {
//            // Простейший HTTP запрос без библиотек (Java 1.8+)
//            java.net.URL url = new java.net.URL("http://localhost:8080/people");
//            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(3000);
//
//            int responseCode = conn.getResponseCode();
//            System.out.println("HTTP код ответа: " + responseCode);
//
//            if (responseCode == 200) {
//                System.out.println("✅ Spring Boot сервер работает корректно!");
//
//                // Читаем ответ
//                java.util.Scanner scanner = new java.util.Scanner(conn.getInputStream());
//                if (scanner.hasNextLine()) {
//                    System.out.println("Ответ сервера: " + scanner.nextLine());
//                }
//                scanner.close();
//            }
//
//            conn.disconnect();
//
//        } catch (Exception e) {
//            System.out.println("Ошибка HTTP запроса: " + e.getMessage());
//        }
//    }
}