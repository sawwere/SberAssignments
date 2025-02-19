package com.sawwere.sber.homework18.crud.assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TopicRestControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void whenGetTopic_thenStatus200AndContentTypeJson() {
        given().when()
                .get("/api/v1/topics/1")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    public void whenGetTopic_thenContainsCorrectInfoForSpecificUser() {
        given().when()
                .get("/api/v1/topics/1")
                .then()
                .body("id", equalTo(1))
                .body("author", equalTo("Админ"))
                .body("title", equalTo("Первый пост"))
                .body("text", equalTo("Здесь должен быть текст"))
        ;
    }

    @Test
    public void whenGetNotExistingTopic_thenStatus404() {
        given().when()
                .get("/api/v1/topics/40000000")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    public void whenGetAllTopics_thenHasCorrectResponseBody() {
        given().when()
                .get("/api/v1/topics")
                .then()
                .body("$", hasSize(greaterThan(0)))
                .body("id", notNullValue())
                .body("author", notNullValue())
                .body("title", notNullValue())
                .body("text", notNullValue())
        ;
    }

    @Test
    public void whenCreateTopic_thenStatus201AndCorrectResponseBody() {
        String newTopic = """
                {
                    "author": "nea author",
                    "title": "new title",
                    "text": "some text"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(newTopic)
                .when()
                .post("/api/v1/topics")
                .then()
                .statusCode(201)
                .and()
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("author", equalTo("nea author"))
                .body("title", equalTo("new title"))
                .body("text", equalTo("some text"))
        ;
    }

    @Test
    public void whenCreateTopicWithInvalidTitle_thenStatus400() {
        String newTopic = """
                {
                    "author": "",
                    "title": "new title",
                    "text": "some text"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(newTopic)
                .when()
                .post("/api/v1/topics")
                .then()
                .statusCode(400)
        ;
    }
}
