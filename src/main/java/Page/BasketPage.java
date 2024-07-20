package Page;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Slf4j
public class BasketPage {

    // Переменные на основне селекторов элементов
    private final SelenideElement basketRemoveButton = $("[class='basket-item-actions-remove']");
    private final SelenideElement placingAnOrderButton = $("[id='BasketDefaultOrderAuth']");

    // Метод для удаления товара из корзины
    public void deleteInBasket() {
        basketRemoveButton.shouldBe(visible)
                .hover()
                .click();
    }

    // Метод нажатия на кнопку оформления заказа
    public void placingAnOrder() {
        placingAnOrderButton.shouldBe(visible)
                .hover()
                .click();
    }

    // Методы для сравнения
    public void assertBasketEmpty() {
        $x("//*[contains(text(), 'Ваша корзина пуста')]").shouldBe(visible);
        log.info("Проверка, что корзина пуста");
    }

    public void assertPageAuthorization() {
        $x("//*[contains(text(), 'Для продолжения необходимо авторизоваться.')]").shouldBe(visible);
        log.info("Проверка, что произошел переход на страницу авторизации");
    }

    public void assertWrongNumber() {
        $x("//*[contains(text(), 'Указан некорректный номер телефона.')]").shouldBe(visible);
        log.info("Проверка, что произошел номер не корректный");
    }

    // Метод для установки номера в поле
    public void setValueNumber() {
        $("#USER_LOGIN_POPUP").setValue("0000000000").pressEnter();;
    }
}
