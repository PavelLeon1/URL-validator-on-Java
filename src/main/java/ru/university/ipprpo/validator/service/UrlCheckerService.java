package ru.university.ipprpo.validator.service;

import ru.university.ipprpo.validator.config.AppConfig;
import ru.university.ipprpo.validator.model.UrlResponse;
import ru.university.ipprpo.validator.model.UrlStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

/**
 * Сервис, отвечающий за проверку доступности URL-адресов.
 */
public class UrlCheckerService {

    private final HttpClient httpClient;
    private final String httpMethod;
    private final Duration timeout;

    /**
     * Конструктор инициализирует HTTP-клиент с настройками из конфигурации.
     */
    public UrlCheckerService() {
        this.httpMethod = AppConfig.getProperty("http.method");
        this.timeout = Duration.ofMillis(AppConfig.getIntProperty("http.timeout.ms"));
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER) // Не следуем за редиректами, чтобы видеть 3xx коды
                .build();
    }

    /**
     * Проверяет один URL-адрес.
     * @param urlString URL для проверки.
     * @return {@link UrlResponse} с результатом проверки.
     */
    public UrlResponse checkUrl(String urlString) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .timeout(timeout);

            if ("HEAD".equalsIgnoreCase(httpMethod)) {
                requestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
            } else {
                requestBuilder.GET();
            }

            HttpRequest request = requestBuilder.build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            int statusCode = response.statusCode();
            UrlStatus status = classifyStatusCode(statusCode);
            return new UrlResponse(urlString, statusCode, status);

        } catch (HttpTimeoutException e) {
            return new UrlResponse(urlString, 0, UrlStatus.TIMEOUT);
        } catch (Exception e) {
            // Пояснение: Сюда попадают ошибки DNS, SSL, IO и прочие.
            return new UrlResponse(urlString, 0, UrlStatus.UNKNOWN_ERROR);
        }
    }

    /**
     * Классифицирует HTTP код ответа.
     * @param statusCode HTTP код.
     * @return {@link UrlStatus} - категория статуса.
     */
    private UrlStatus classifyStatusCode(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return UrlStatus.OK;
        } else if (statusCode >= 300 && statusCode < 400) {
            return UrlStatus.REDIRECTION;
        } else if (statusCode >= 400 && statusCode < 500) {
            return UrlStatus.CLIENT_ERROR;
        } else if (statusCode >= 500 && statusCode < 600) {
            return UrlStatus.SERVER_ERROR;
        }
        return UrlStatus.UNKNOWN_ERROR;
    }
}