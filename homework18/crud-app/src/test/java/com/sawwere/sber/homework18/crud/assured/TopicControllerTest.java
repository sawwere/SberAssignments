package com.sawwere.sber.homework18.crud.assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TopicControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void whenGetTopicPage_thenStatus200AndContentTypeHtml() {
        given().when()
                .get("/topics/1")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.HTML)
        ;
    }

    @Test
    public void whenGetNotExistingTopicPage_thenStatus404AndContentTypeHtml() {
        given().when()
                .get("/topics/4000000")
                .then()
                .statusCode(404)
                .and()
                .contentType(ContentType.HTML)
        ;
    }

    @Test
    public void whenGetAllTopicsPage_thenStatus200AndContentTypeHtml() {
        given().when()
                .get("/topics")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.HTML)
        ;
    }

    @Test
    public void whenCreateTopic_thenStatus200AndCorrectPage() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("author", "nea author")
                .formParam("title", "new title")
                .formParam("text", "tex_xx_xt")
                .when()
                .post("/topics")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.HTML)
                .body("html.head.title",equalTo("new title"))
        ;
    }
}
