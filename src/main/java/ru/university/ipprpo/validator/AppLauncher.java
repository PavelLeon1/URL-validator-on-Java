package ru.university.ipprpo.validator;

import ru.university.ipprpo.validator.config.AppConfig;
import ru.university.ipprpo.validator.model.UrlResponse;

import ru.university.ipprpo.validator.provider.FileUrlProvider;
import ru.university.ipprpo.validator.provider.UrlProvider;
import ru.university.ipprpo.validator.service.ReportGenerator;
import ru.university.ipprpo.validator.service.UrlCheckerService;

import java.io.IOException;
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

            // создаем экземпляр конкретной реализации провайдера
            String filePath = AppConfig.getProperty("urls.file.path");
            UrlProvider urlProvider = new FileUrlProvider(filePath);

            List<String> urls;
            try {
                // URL через интерфейс, не зная, откуда они берутся
                urls = urlProvider.getUrls();
            } catch (IOException e) {
                System.err.println("Ошибка: не удалось прочитать данные от провайдера URL: " + e.getMessage());
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
}