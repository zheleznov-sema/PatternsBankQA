package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginTest {

    @Test
    void shouldRegisterActiveUser() {
        var user = DataGenerator.getRegisteredUser();

        DataGenerator.registerUser(user);
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button")
                .findBy(Condition.exactText("Продолжить"))
                .click();
        webdriver().shouldHave(url("http://localhost:9999/dashboard"));
    }

    @Test
    void shouldNotLoginBlockedUser() {
        var user = DataGenerator.getBlockedUser();

        DataGenerator.registerUser(user);

        open("http://localhost:9999");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());

        $$("button")
                .findBy(Condition.exactText("Продолжить"))
                .click();
        $("[data-test-id='error-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        open("http://localhost:9999");

        $("[data-test-id='login'] input").setValue("unknown-user");
        $("[data-test-id='password'] input").setValue("password");

        $$("button")
                .findBy(Condition.exactText("Продолжить"))
                .click();
        $("[data-test-id='error-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        var user = DataGenerator.getRegisteredUser();

        DataGenerator.registerUser(user);

        open("http://localhost:9999");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue("wrong-password");

        $$("button")
                .findBy(Condition.exactText("Продолжить"))
                .click();
        $("[data-test-id='error-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}