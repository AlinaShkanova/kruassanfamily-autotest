import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static Util.Constans.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// аннотация для логирования
@Slf4j
public class CheckBasketTest extends TestBase {


    // предварительные действия перед каждым тестом
    @BeforeEach
    public void beforeEach() {
        // открытие страницы с выбором продукции
        open(MAIN_URL + SALTY_PIES_URN);
        // ожидание открытия страницы
        Duration.ofSeconds(30);
    }


    // аннотация для названия теста
    @DisplayName("Добавление пирога в корзину и проверка что он добавлен")
    // аннотация - порядок выполнения
    @Order(1)
    @Test
    public void testSelectPies(){
        // Переходим на страницу с пирогами и выбираем нужный товар
        menuPage.clickConfirmButton();
        menuPage.selectPieWithTomato();
        // Добавляем товар в корзину
        menuPage.addInBasket();
        // Проверка, что товар добавлен в корзину
        menuPage.assertAddInBasket();
    }

    @DisplayName("Удаление пирога из корзины и проверка что корзина пустая")
    @Order(2)
    @Test
    public void testDeletePiesInBasket(){
        // Переходим на страницу с пирогами и выбираем нужный товар
        menuPage.clickConfirmButton();
        menuPage.selectPieWithTomato();
        // Добавляем товар в корзину
        menuPage.addInBasket();
        // Проверка, что товар добавлен в корзину
        menuPage.assertAddInBasket();
        // Удаление товара из корзины
        basketPage.deleteInBasket();
        // Проверка, что корзина пустая
        basketPage.assertBasketEmpty();
    }

    @DisplayName("Негативный кейс. Оформление заказа")
    @Order(3)
    @Test
    public void testPlacingAnOrder(){
        // Переходим на страницу с пирогами и выбираем нужный товар
        menuPage.clickConfirmButton();
        menuPage.selectPieWithTomato();
        // Добавляем товар в корзину
        menuPage.addInBasket();
        // Проверка, что товар добавлен в корзину
        menuPage.assertAddInBasket();
        // Нажать на кнопку оформления заказа
        basketPage.placingAnOrder();
        // Проверка, что произошел переход на страницу авторизации
        basketPage.assertPageAuthorization();
        // Введение номера телефона
        basketPage.setValueNumber();
        // Проверка что номер неккоректный
        basketPage.assertWrongNumber();
    }

    @DisplayName("Проверка валидности отображения суммы в корзине")
    @Order(4)
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void testValidSumPiesInBasket(String count) {
        int itemCount = Integer.parseInt(count);
        // Переходим на страницу с пирогами и выбираем нужный товар
        menuPage.clickConfirmButton();
        menuPage.selectPieWithTomato();
        // Добавляем товар в корзину и переходим в корзину
        menuPage.addInBasket();
        // Проверка, что товар добавлен в корзину
        menuPage.assertAddInBasket();
        // Добавляем дополнительные товары в корзину
        for (int i = 0; i < itemCount - 1; i++) {
            menuPage.addInBasketPie();
        }
        // Рассчитываем ожидаемую сумму в корзине, учитывая уже добавленные товары
        double expectedTotalPrice = menuPage.getTotalPriceInBasket();
        expectedTotalPrice += THE_PRICE_OF_THE_PRODUCT * (itemCount - 1);
        // Получаем отображаемую сумму в корзине
        double actualTotalPrice = menuPage.getTotalPriceInBasket();
        // Проверяем, что отображаемая сумма соответствует ожидаемой
        assertEquals(expectedTotalPrice, actualTotalPrice, "Сумма в корзине не соответствует ожидаемой");

        log.info("Тест пройден: количество товаров = {}, ожидаемая сумма = {}, отображаемая сумма = {}",
                itemCount, expectedTotalPrice, actualTotalPrice);
    }
}
