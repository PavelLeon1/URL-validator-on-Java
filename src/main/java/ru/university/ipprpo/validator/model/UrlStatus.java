package ru.university.ipprpo.validator.model;

/**
 * Перечисление, представляющее классификацию статуса ответа HTTP.
 */
public enum UrlStatus {
    OK,           // Коды 2xx
    REDIRECTION,  // Коды 3xx
    CLIENT_ERROR, // Коды 4xx
    SERVER_ERROR, // Коды 5xx
    TIMEOUT,      // Превышен таймаут запроса
    UNKNOWN_ERROR // Прочие ошибки (например, IO)
}