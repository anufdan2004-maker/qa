package ui;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * UI-тесты для учебного сайта saucedemo.com — стандартный демо-стенд для практики
 * автоматизации (используется в обучающих курсах по QA/AQA).
 */
public class LoginTest {

    private static final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true; // без открытия окна браузера — удобно для CI
        Configuration.timeout = 6000;
        open(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    @DisplayName("Успешный логин со стандартным пользователем")
    void standardUser_shouldLoginSuccessfully() {
        $("#user-name").setValue("standard_user");
        $("#password").setValue("secret_sauce");
        $("#login-button").click();

        $(".inventory_list").shouldBe(visible);
        $(".title").shouldHave(text("Products"));
    }

    @Test
    @DisplayName("Логин с заблокированным пользователем — ожидаем сообщение об ошибке")
    void lockedOutUser_shouldShowErrorMessage() {
        $("#user-name").setValue("locked_out_user");
        $("#password").setValue("secret_sauce");
        $("#login-button").click();

        $("[data-test='error']").shouldBe(visible)
                .shouldHave(text("Sorry, this user has been locked out"));
    }

    @Test
    @DisplayName("Логин с неверным паролем — ожидаем сообщение об ошибке")
    void wrongPassword_shouldShowErrorMessage() {
        $("#user-name").setValue("standard_user");
        $("#password").setValue("wrong_password");
        $("#login-button").click();

        $("[data-test='error']").shouldBe(visible)
                .shouldHave(text("Username and password do not match"));
    }

    @Test
    @DisplayName("Добавление товара в корзину после успешного логина")
    void standardUser_shouldAddItemToCart() {
        $("#user-name").setValue("standard_user");
        $("#password").setValue("secret_sauce");
        $("#login-button").click();

        $(".inventory_item:first-child .btn_inventory").click();

        $(".shopping_cart_badge").shouldHave(text("1"));
    }
}
