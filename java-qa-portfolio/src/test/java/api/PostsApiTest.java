package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * API-тесты для публичного сервиса JSONPlaceholder (https://jsonplaceholder.typicode.com).
 */
public class PostsApiTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @DisplayName("GET /posts/1 — получение конкретного поста")
    void getSinglePost_shouldReturnCorrectPost() {
        given()
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("userId", notNullValue())
            .body("title", not(emptyString()));
    }

    @Test
    @DisplayName("GET /posts — список постов не пустой и содержит 100 записей")
    void getAllPosts_shouldReturnFullList() {
        given()
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()", equalTo(100));
    }

    @Test
    @DisplayName("GET /posts/9999 — запрос несуществующего поста")
    void getNonExistentPost_shouldReturn404() {
        given()
        .when()
            .get("/posts/9999")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("POST /posts — создание нового поста")
    void createPost_shouldReturnCreatedEntity() {
        String requestBody = """
                {
                    "title": "QA автотест",
                    "body": "Проверка создания поста через REST Assured",
                    "userId": 1
                }
                """;

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .post("/posts")
                .then()
                    .statusCode(201)
                    .body("title", equalTo("QA автотест"))
                    .body("userId", equalTo(1))
                    .extract().response();

        int newId = response.jsonPath().getInt("id");
        org.junit.jupiter.api.Assertions.assertEquals(101, newId);
    }

    @Test
    @DisplayName("PUT /posts/1 — обновление существующего поста")
    void updatePost_shouldReturnUpdatedEntity() {
        String requestBody = """
                {
                    "id": 1,
                    "title": "Обновлённый заголовок",
                    "body": "Обновлённое тело поста",
                    "userId": 1
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .put("/posts/1")
        .then()
            .statusCode(200)
            .body("title", equalTo("Обновлённый заголовок"));
    }

    @Test
    @DisplayName("DELETE /posts/1 — удаление поста")
    void deletePost_shouldReturn200() {
        given()
        .when()
            .delete("/posts/1")
        .then()
            .statusCode(200);
    }
}
