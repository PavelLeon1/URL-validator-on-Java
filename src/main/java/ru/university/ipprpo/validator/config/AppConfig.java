package ru.university.ipprpo.validator.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для загрузки и предоставления доступа к конфигурации приложения из app.properties.
 */
public class AppConfig {

    private static final Properties properties = new Properties();

    // Статический блок для загрузки конфигурации при первом обращении к классу.
    static {
        try (InputStream input = new FileInputStream("./config/app.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            // Пояснение: В реальном приложении здесь была бы более сложная обработка ошибок.
            // Для лабораторной работы достаточно вывести ошибку в консоль.
            System.err.println("Ошибка: Не удалось загрузить файл конфигурации app.properties.");
            // Завершаем работу, так как без конфигурации приложение неработоспособно.
            System.exit(1);
        }
    }

    /**
     * Возвращает значение свойства по его ключу.
     * @param key Ключ свойства.
     * @return Значение свойства в виде строки.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Возвращает значение свойства по ключу, преобразуя его в целое число.
     * @param key Ключ свойства.
     * @return Целочисленное значение свойства.
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}