package api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    @DisplayName("Создание пользователя POST api/users с валидными name и job")
    void successfulCreateUserTest() {
        String body = "{ \"name\": \"irina\", \"job\": \"qa\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("irina"))
                .body("job", is("qa"));
    }

    @Test
    @DisplayName("Изменение имени пользователя PUT api/users/:id с существующим id")
    void successfulUpdateUserTest() {
        String body = "{ \"name\": \"ira\", \"job\": \"qa\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/6")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("ira"));
    }

    @Test
    @DisplayName("Удаление пользователя DELETE api/users/:id с существующим id")
    void successfulDeleteUserTest() {
        given()
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/6")
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    @DisplayName("Проверка структуры ответа на запрос GET api/users/:id с id существующего пользователя")
    void successfulCheckSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/10")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemes/status-scheme-responce.json"));
    }

    @Test
    @DisplayName("Регистрация пользователя POST /api/register без password")
    void unsuccessfulRegisterMissingPasswordTest() {
        String body = "{ \"email\": \"irina@mail.ru\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}