package com.sawwere.sber.homework18.crud.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Посты")
@Feature("Topic Controller")
public class TopicControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @Story("Получение страницы поста")
    @Description("Проверка того, что ответ на запрос на получение страницы поста имеет статус 200 и имеет заголовок Content-Type HTML")
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
    @Story("Получение страницы несуществующего поста")
    @Description("Проверка того, что ответ на запрос на получение страницы несуществующего поста имеет статус 404 и имеет заголовок Content-Type HTML")
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
    @Story("Получение страницы со списком всех постов")
    @Description("Проверка того, что ответ на запрос на получение страницы со списком всех постов имеет статус 200 и имеет заголовок Content-Type HTML")
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
    @Story("Создание нового поста через форму")
    @Description("Проверка того, создание поста через форму работает корректно и возвращает страницу с новосозданным постом")
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
