package ru.university.ipprpo.validator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.university.ipprpo.validator.model.UrlStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit-тесты для класса {@link UrlCheckerService}.
 * Основное внимание уделено тестированию логики классификации кодов ответа.
 */
@DisplayName("Тестирование сервиса проверки URL")
class UrlCheckerServiceTest {

    private UrlCheckerService urlCheckerService;

    /**
     * Этот метод выполняется перед каждым тестовым методом (@Test).
     * Он создает новый экземпляр сервиса, чтобы тесты были независимыми друг от друга.
     * Примечание: для этого теста требуется наличие файла config/app.properties,
     * так как конструктор UrlCheckerService его читает.
     */
    @BeforeEach
    void setUp() {
        urlCheckerService = new UrlCheckerService();
    }

    @Test
    @DisplayName("Код 200 (OK) должен классифицироваться как OK")
    void classifyStatusCode_when200_shouldReturnOk() {
        // Arrange (Подготовка)
        int statusCode = 200;

        // Act (Действие)
        UrlStatus result = urlCheckerService.classifyStatusCode(statusCode);

        // Assert (Проверка)
        assertEquals(UrlStatus.OK, result, "Статус для кода 200 должен быть OK");
    }

    @Test
    @DisplayName("Код 301 (Moved Permanently) должен классифицироваться как REDIRECTION")
    void classifyStatusCode_when301_shouldReturnRedirection() {
        // Arrange
        int statusCode = 301;

        // Act
        UrlStatus result = urlCheckerService.classifyStatusCode(statusCode);

        // Assert
        assertEquals(UrlStatus.REDIRECTION, result, "Статус для кода 301 должен быть REDIRECTION");
    }

    @Test
    @DisplayName("Код 404 (Not Found) должен классифицироваться как CLIENT_ERROR")
    void classifyStatusCode_when404_shouldReturnClientError() {
        // Arrange
        int statusCode = 404;

        // Act
        UrlStatus result = urlCheckerService.classifyStatusCode(statusCode);

        // Assert
        assertEquals(UrlStatus.CLIENT_ERROR, result, "Статус для кода 404 должен быть CLIENT_ERROR");
    }

    @Test
    @DisplayName("Код 503 (Service Unavailable) должен классифицироваться как SERVER_ERROR")
    void classifyStatusCode_when503_shouldReturnServerError() {
        // Arrange
        int statusCode = 503;

        // Act
        UrlStatus result = urlCheckerService.classifyStatusCode(statusCode);

        // Assert
        assertEquals(UrlStatus.SERVER_ERROR, result, "Статус для кода 503 должен быть SERVER_ERROR");
    }

    @Test
    @DisplayName("Нестандартный код (например, 101) должен классифицироваться как UNKNOWN_ERROR")
    void classifyStatusCode_when101_shouldReturnUnknownError() {
        // Arrange
        int statusCode = 101;

        // Act
        UrlStatus result = urlCheckerService.classifyStatusCode(statusCode);

        // Assert
        assertEquals(UrlStatus.UNKNOWN_ERROR, result, "Неизвестный код должен получать статус UNKNOWN_ERROR");
    }
}