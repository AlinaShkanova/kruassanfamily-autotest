package Page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;

import static Util.Methods.waitForElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class MenuPage {
    private final SelenideElement pieWithTomato = Selenide.$x("//*[contains(text(), 'Киш с томатами и моцареллой')]");
    private final SelenideElement confirmButton = Selenide.$x("//*[contains(text(), 'Подтвердить')]");
    private final String textInBasket = "В корзину";
    private final SelenideElement selectButton =
            Selenide.$x("//*[@id=\"main\"]/div[6]/div[7]/div/div[2]/div/div/div[1]");
    private final SelenideElement streetButton =
            Selenide.$x("//*[@id=\"main\"]/div[6]/div[7]/div/div[2]/div/div/div[2]/div/div[2]/span");


    public void clickConfirmButton() {
        waitForElement(confirmButton);
        selectButton.shouldBe(visible).hover().click();
        streetButton.shouldBe(visible).hover().click();
        confirmButton.shouldBe(visible);
        confirmButton.hover().click();
    }

    public void selectPieWithTomato() {
        waitForElement(pieWithTomato);
        pieWithTomato.shouldBe(visible);
        pieWithTomato.hover().doubleClick();
    }

    public void addInBasket() {
        String cssAddBasket = "span.btn-lg.no-icons.to-cart";
        $(cssAddBasket).shouldHave(text(textInBasket)).click();
    }

    public void addInBasketPie() {
        String cssAddBasket = "[data-entity='basket-item-quantity-plus']";
        $(cssAddBasket).hover().click();
    }

    public void assertAddInBasket() {
        $("a.btn-lg.no-icons.in-cart").shouldHave(text("В корзине")).click();
        log.info("Проверка, что товар добавлен в корзину");
    }

    public double getTotalPriceInBasket() {
        SelenideElement totalPriceElement = $("[class='basket-coupon-block-total-price-current']");
//        SelenideElement totalPriceElement = $(".basket-coupon-block-total-price-current");

        // Получаем текст из элемента и убираем лишние символы (например, валютный знак)
        String totalPriceText = totalPriceElement.getText().replace("₽", "").replace(" ",
                "");
        // Преобразуем текст в число и возвращаем
        return Double.parseDouble(totalPriceText);
    }
}
