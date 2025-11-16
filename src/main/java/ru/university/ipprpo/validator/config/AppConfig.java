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

    // статический блок для загрузки конфигурации при первом обращении к классу.
    static {
        String propFileName = "app.properties";
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(propFileName)) {

            if (input == null) {
                System.err.println("Ошибка: Не удалось найти файл конфигурации '" + propFileName + "' в ресурсах.");
                System.exit(1);
            }
            properties.load(input);

        } catch (IOException ex) {
            System.err.println("Ошибка: Не удалось загрузить файл конфигурации app.properties.");
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