package ru.university.ipprpo.validator.service;

import ru.university.ipprpo.validator.model.UrlResponse;
import ru.university.ipprpo.validator.model.UrlStatus;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для генерации и вывода отчета о проверке URL в консоль.
 */
public class ReportGenerator {

    /**
     * Печатает отчет на основе списка результатов.
     * @param results Список {@link UrlResponse}.
     */
    public void printReport(List<UrlResponse> results) {
        // Stream API для фильтрации и вывода.
        List<UrlResponse> failedUrls = results.stream()
                .filter(r -> r.status() != UrlStatus.OK && r.status() != UrlStatus.REDIRECTION)
                .collect(Collectors.toList());

        if (!failedUrls.isEmpty()) {
            System.out.println("--- FAILED URLS ---");
            failedUrls.forEach(r ->
                    System.out.printf("[%s: %d] %s%n", r.status(), r.statusCode(), r.url())
            );
        }

        List<UrlResponse> okUrls = results.stream()
                .filter(r -> r.status() == UrlStatus.OK || r.status() == UrlStatus.REDIRECTION)
                .collect(Collectors.toList());

        if (!okUrls.isEmpty()) {
            System.out.println("\n--- OK URLS ---");
            okUrls.forEach(r ->
                    System.out.printf("[%s: %d] %s%n", r.status(), r.statusCode(), r.url())
            );
        }
    }
}