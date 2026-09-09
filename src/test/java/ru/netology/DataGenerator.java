package ru.netology;

import com.github.javafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static RegistrationDto getUser(String status) {
        return new RegistrationDto(
                faker.name().username(),
                faker.internet().password(),
                status
        );
    }

    public static String getInvalidPassword() {
        return faker.internet().password();
    }

    public static void registerUser(RegistrationDto user) {
        given()
                .baseUri("http://localhost:9999")
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}