package ru.university.ipprpo.validator;

import ru.university.ipprpo.validator.config.AppConfig;
import ru.university.ipprpo.validator.model.UrlResponse;
import ru.university.ipprpo.validator.service.ReportGenerator;
import ru.university.ipprpo.validator.service.UrlCheckerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Главный класс приложения. Отвечает за руководство всего процесса.
 */
public class AppLauncher {

    /**
     * Главный метод для запуска приложения валидации URL.
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting URL validation...");

            // путь к файлу из конфигурации
            String filePath = AppConfig.getProperty("urls.file.path");
            List<String> urls;
            try {
                // Читаем файл как ресурс
                urls = readLinesFromResource(filePath);
            } catch (IOException e) {
                System.err.println("Ошибка: не удалось прочитать файл со списком URL из ресурсов: " + filePath);
                return;
            }

            // инициализируем сервисы
            UrlCheckerService checkerService = new UrlCheckerService();
            ReportGenerator reportGenerator = new ReportGenerator();

            // проверка каждого URL и сбор резултатов
            // parallelStream() позволяет выполнять запросы параллельно, ускоряя работу.
            List<UrlResponse> results = urls.parallelStream()
                    .map(checkerService::checkUrl)
                    .collect(Collectors.toList());

            // печать ответа
            reportGenerator.printReport(results);
            System.out.println("\nValidation finished.");

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Вспомогательный метод для чтения всех строк из файла в ресурсах.
     * @param path Путь к файлу внутри папки resources.
     * @return Список строк из файла.
     * @throws IOException если файл не найден или не может быть прочитан.
     */
    private static List<String> readLinesFromResource(String path) throws IOException {
        InputStream inputStream = AppLauncher.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Ресурс не найден: " + path);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}