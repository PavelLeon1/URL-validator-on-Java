package ru.university.ipprpo.validator.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация {@link UrlProvider}, которая читает URL-адреса из файла в ресурсах.
 */
public class FileUrlProvider implements UrlProvider {

    private final String resourcePath;

    /**
     * Конструктор.
     * @param resourcePath Путь к файлу со списком URL внутри папки resources.
     */
    public FileUrlProvider(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * {@inheritDoc}
     * Читает все строки из файла в ресурсах.
     */
    @Override
    public List<String> getUrls() throws IOException {
        InputStream inputStream = FileUrlProvider.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Ресурс не найден: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}