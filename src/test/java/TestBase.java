import Page.BasketPage;
import Page.MenuPage;
import Util.Constans;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.openqa.selenium.devtools.v116.browser.Browser.close;

public class TestBase {

    @AfterEach
    @DisplayName("После каждого теста")
    public void tearDown() {
        // Закрываем страницу
        close();
        closeWebDriver();
    }

    // Аннотация JUnit, указывающая, что метод должен выполняться один раз перед всеми тестами в классе.
    @BeforeAll
    @DisplayName("Установка конфигураций браузера")
    public static void setUpBeforeAll() {
        // Инициализация настроек браузера с использованием DesiredCapabilities.
        // DesiredCapabilities позволяет задавать различные свойства для браузера.
        Configuration.browserCapabilities = new DesiredCapabilities();
        // Создание объекта ChromeOptions для настройки опций браузера Chrome.
        ChromeOptions options = new ChromeOptions();
        // Установка ChromeOptions как capability (возможность) для браузера.
        // Это позволяет задать конкретные опции для браузера Chrome.
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        // Headless режим означает запуск браузера без графического интерфейса.
        // Здесь устанавливается обычный режим с графическим интерфейсом.
        Configuration.headless = false;
        // Установка размера окна браузера.
        // Значение берется из константы Constans.BROWSER_SIZE.
        Configuration.browserSize = Constans.BROWSER_SIZE;
    }

    // Защищенные переменные (объекты классов)
    protected BasketPage basketPage = new BasketPage();
    protected MenuPage menuPage = new MenuPage();
}
