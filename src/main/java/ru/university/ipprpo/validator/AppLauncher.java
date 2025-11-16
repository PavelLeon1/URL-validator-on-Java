package ru.university.ipprpo.validator;

import ru.university.ipprpo.validator.config.AppConfig;
import ru.university.ipprpo.validator.model.UrlResponse;
import ru.university.ipprpo.validator.service.ReportGenerator;
import ru.university.ipprpo.validator.service.UrlCheckerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Главный класс приложения. Отвечает за руководство всего процесса.
 */
public class AppLauncher {

    public static void main(String[] args) {
        System.out.println("Starting URL validation...");

        // путь к файлу из конфигурации
        String filePath = AppConfig.getProperty("urls.file.path");
        List<String> urls;
        try {
            urls = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Ошибка: не удалось прочитать файл со списком URL: " + filePath);
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
    }
}