package Util;

import com.codeborne.selenide.SelenideElement;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;

import static Util.Constans.CONFIGURATION_TIMEOUT;
import static Util.Constans.RETRIES;

@Slf4j
public class Methods {

    @Description("Ожидание загрузки элемента")
    // Объявление статического метода с возвращаемым значением типа SelenideElement и параметром element типа SelenideElement
    public static SelenideElement waitForElement(SelenideElement element) {
        // Инициализация переменной retryCount, определяющей количество попыток
        int retryCount = 0;
        // Инициализация переменной maxRetries, определяющей максимальное количество попыток
        int maxRetries = RETRIES;
        // Инициализация переменной sleepMillis, определяющей время ожидания между попытками
        long sleepMillis = CONFIGURATION_TIMEOUT;
        // Начало цикла while, который будет повторяться до достижения максимального количества попыток
        while (retryCount < maxRetries) {
            // Начало блока try-catch для обработки возможных исключений
            try {
                // Проверка существования элемента
                if (element.exists()) {
                    // Вывод информации о найденном элементе
                    log.info("Элемент найден");
                    // Возврат найденного элемента
                    return element;
                }
                // Обработка исключения, если произошла ошибка при попытке проверить существование элемента
            } catch (Exception e) {
                log.info("Ошибка при попытке проверить существование элемента: {}", e.getMessage());
            }
            // Вывод информации о том, что элемент не был найден, и номер текущей попытки
            log.info("Элемент не найден. Попытка номер {}", retryCount + 1);
            // Ожидание перед следующей попыткой
            sleep(sleepMillis);
            // Увеличение счетчика попыток
            retryCount++;
        }
        // Возврат значения null, если элемент так и не был найден
        return null;
    }

    private static void sleep(long sleepMillis) {
        // Начало блока try-catch для обработки исключения InterruptedException
        try {
            // Вызов статического метода sleep класса Thread для приостановки выполнения
            // текущего потока на указанное количество миллисекунд
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            log.info("Прервано ожидание: {}", e.getMessage());
        }
    }
}
