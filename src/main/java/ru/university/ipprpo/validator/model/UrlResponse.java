package ru.university.ipprpo.validator.model;

/**
 * Модель данных для хранения результата проверки URL.
 * Используется record для неизменяемости и краткости.
 *
 * @param url Исходный URL.
 * @param statusCode HTTP код ответа (0 для ошибок).
 * @param status Классифицированный статус из {@link UrlStatus}.
 */
public record UrlResponse(String url, int statusCode, UrlStatus status) {
}