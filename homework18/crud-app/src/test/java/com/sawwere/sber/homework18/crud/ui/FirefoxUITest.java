package com.sawwere.sber.homework18.crud.ui;

import com.sawwere.sber.homework18.crud.controller.ForumController;
import com.sawwere.sber.homework18.crud.controller.TopicGetController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;


public class FirefoxUITest {
    private static final String URL_PREFIX = "http://localhost:8080";
    private static final FirefoxOptions defaultOptions = new FirefoxOptions()
            .addArguments("--headless");


    @ParameterizedTest
    @ValueSource(strings = {ForumController.INDEX, TopicGetController.SHOW_TOPICS})
    public void testPageTitle(String url) {
        WebDriver driver = new FirefoxDriver(defaultOptions);
        driver.get(URL_PREFIX + url);

        Assertions.assertEquals("Форум", driver.getTitle());
    }


    @Test
    public void testUpdateTopicPage() {
        WebDriver driver = new FirefoxDriver(defaultOptions);
        driver.get(URL_PREFIX + TopicGetController.GET_UPDATE_TOPIC_PAGE.replace("{id}", "1"));

        // поля формы должны быть заполнены текущими данными поста
        WebElement author = driver.findElement(By.id("author"));
        Assertions.assertEquals("Админ", author.getAttribute("value"));
        WebElement title = driver.findElement(By.id("title"));
        Assertions.assertEquals("Первый пост", title.getAttribute("value"));
        WebElement text = driver.findElement(By.id("text"));
        Assertions.assertEquals("Здесь должен быть текст", text.getAttribute("value"));
    }

    @Test
    public void testGetTopicPage() {
        WebDriver driver = new FirefoxDriver(defaultOptions);
        driver.get(URL_PREFIX + TopicGetController.GET_TOPIC.replace("{id}", "1"));

        WebElement author = driver.findElement(By.className("topicAuthor"));
        Assertions.assertEquals("Создан пользователем Админ", author.getText());
        WebElement title = driver.findElement(By.className("topicTitle"));
        Assertions.assertEquals("Первый пост", title.getText());
        WebElement text = driver.findElement(By.className("topicText"));
        Assertions.assertEquals("Здесь должен быть текст", text.getText());
    }

    @Test
    public void testShowTopicsPage() {
        WebDriver driver = new FirefoxDriver(defaultOptions);
        driver.get(URL_PREFIX + TopicGetController.SHOW_TOPICS);

        List<WebElement> elements = driver.findElements(By.className("topicComponent"));
        // проверяем, что все посты имеют требуемую структуру дочерних элементов
        for (WebElement element : elements) {
            var childElements = element.findElements(By.xpath("./*"));
            Assertions.assertEquals(2, childElements.size());

            Assertions.assertEquals("div", childElements.get(0).getTagName());
            Assertions.assertEquals("topicPreview", childElements.get(0).getAttribute("class"));

            Assertions.assertEquals("div", childElements.get(1).getTagName());
            Assertions.assertEquals("topicLinks", childElements.get(1).getAttribute("class"));
        }
    }

    @Test
    public void testShowTopicsPageViewTopicHref() {
        WebDriver driver = new FirefoxDriver(defaultOptions);
        driver.get(URL_PREFIX + TopicGetController.SHOW_TOPICS);

        // Нужно найти любой из постов и нажать на "Перейти" для него.
        // У каждого поста есть 3 ссылки в компоненте topicLinks, надо нажать на первую из них.
        // Она и должна переводить на страницу поста
        WebElement topicViewLinkElement = driver.findElement(By.xpath("//div[contains(@class, 'topicLinks')]/*[1]"));

        Assertions.assertEquals("Перейти", topicViewLinkElement.getText());

        topicViewLinkElement.click();
        Assertions.assertTrue(driver.getCurrentUrl().matches("http://localhost:8080/topics/[0-9]+"));
    }
}
