package com.sawwere.sber.homework18.crud.integration;

import com.sawwere.sber.homework18.crud.BasicTestContainerTest;
import com.sawwere.sber.homework18.crud.controller.TopicGetController;
import com.sawwere.sber.homework18.crud.controller.TopicPostController;
import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class TopicIntegrationTest extends BasicTestContainerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private TopicService topicService;

    private final Topic genericTopic = Topic.builder()
            .id(1001L)
            .title("topic title")
            .author("topic author")
            .text("topic text")
            .build();

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(topicService);
    }

    @Test
    @Sql(statements = """
            INSERT INTO topics
                (id, title, text, author)
            VALUES
                (10011, 'пост', 'Здесь должен быть текст', 'admin');
            """,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void getUpdateTopicPage() throws Exception {
        // страница обновления поста должна содержать реальные данный из бд на момент перед обновлением
        mockMvc.perform(get(TopicGetController.GET_UPDATE_TOPIC_PAGE, 10011))
                .andExpect(view().name("topic_update"))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo("пост"))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo("Здесь должен быть текст"))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo("admin")))
                );
    }

    @Test
    public void deleteTopic() throws Exception {
        mockMvc.perform(get(TopicGetController.DELETE_TOPIC, genericTopic.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TopicGetController.SHOW_TOPICS));
        Mockito.verify(topicService, Mockito.atLeastOnce()).delete(genericTopic.getId());
    }

    @ParameterizedTest
    @MethodSource("testTopicsFactory")
    @Sql(scripts = "/sql-scripts/insert_test_topics.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void getTopic(Topic topic) throws Exception {
        // страница поста должна содержать реальные данный из бд
        mockMvc.perform(get(TopicGetController.GET_TOPIC, topic.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("topic"))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo(topic.getTitle()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo(topic.getText()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo(topic.getAuthor())))
                )
        ;
    }

    @Test
    public void getTopicShouldReturn404OnNonExistingTopic() throws Exception {
        mockMvc.perform(get(TopicGetController.GET_TOPIC, 666))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorInfo"))
        ;
    }

    @Sql(scripts = "/sql-scripts/insert_test_topics.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    public void showTopics() throws Exception {
        mockMvc.perform(get(TopicGetController.SHOW_TOPICS))
                .andExpect(status().isOk())
                // должно быть всего 4 поста: 2 из миграции, 2 из скрипта
                .andExpect(model().attribute("topicList", Matchers.hasSize(4)))
        ;
    }

    @Test
    public void createTopic() throws Exception {
        TopicCreationDto dto = TopicCreationDto.builder()
                .title("title tittle")
                .author("author author")
                .text("text text")
                .build();

        mockMvc.perform(post(TopicPostController.CREATE_TOPIC)
                        .formField("title", dto.getTitle())
                        .formField("author", dto.getAuthor())
                        .formField("text", dto.getText()))
                .andExpect(status().isOk())
                .andExpect(view().name("topic"))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo(dto.getTitle()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo(dto.getText()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo(dto.getAuthor())))
                )
        ;
        Mockito.verify(topicService, Mockito.only()).create(Mockito.any());

    }

    @ParameterizedTest
    @MethodSource("testTopicsFactory")
    @Sql(scripts = "/sql-scripts/insert_test_topics.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void updateTopic(Topic topic) throws Exception {
        mockMvc.perform(put(TopicPostController.UPDATE_TOPIC, topic.getId())
                        .formField("title", topic.getTitle() + "_new")
                        .formField("author", topic.getAuthor() +"_new")
                        .formField("text", topic.getText() +"_new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo(topic.getTitle() + "_new"))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo(topic.getText() + "_new"))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo(topic.getAuthor() + "_new")))
                )
        ;
    }

    static Stream<Topic> testTopicsFactory() {
        return Stream.of(Topic.builder()
                        .id(1001)
                        .title("1001 пост")
                        .text("Здесь должен быть текст")
                        .author("Админ")
                        .build(),
                Topic.builder()
                        .id(1002)
                        .title("1002 пост")
                        .text("И здесь тоже должен быть текст")
                        .author("Анонимный пользователь")
                        .build()
        );
    }
}
