package ru.university.ipprpo.validator.provider;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для поставщика URL-адресов.
 * Определяет контракт для любого класса, который может предоставлять список URL для проверки.
 * Это позволяет абстрагироваться от конкретного источника данных (файл, база данных, API и т.д.).
 */
public interface UrlProvider {

    /**
     * Возвращает список URL-адресов из источника.
     *
     * @return Список строк с URL.
     * @throws IOException если возникает ошибка при чтении данных из источника.
     */
    List<String> getUrls() throws IOException;
}