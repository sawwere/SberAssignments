package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link TopicGetController}
 */
@WebMvcTest({TopicGetController.class})
public class TopicGetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    private final Topic genericTopic = Topic.builder()
            .id(1001L)
            .title("title")
            .author("author")
            .text("text")
            .build();

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(topicService);
    }

    @Test
    public void createTopic() throws Exception {
          mockMvc.perform(get(TopicGetController.GET_CREATE_TOPIC_PAGE))
                  .andExpect(status().isOk())
                  .andExpect(view().name("topic_creation"))
                  .andExpect(model().attribute(
                          "newTopic",
                          Matchers.hasProperty("title", Matchers.nullValue())))
                  .andExpect(model().attribute(
                          "newTopic",
                          Matchers.hasProperty("text", Matchers.nullValue())))
                  .andExpect(model().attribute(
                          "newTopic",
                          Matchers.hasProperty("author", Matchers.nullValue())))
          ;
    }

    @Test
    public void updateTopic() throws Exception {
        Mockito.doReturn(genericTopic).when(topicService).findByIdOrElseThrow(genericTopic.getId());

        mockMvc.perform(get(TopicGetController.GET_UPDATE_TOPIC_PAGE, genericTopic.getId()))
                .andExpect(view().name("topic_update"))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo(genericTopic.getTitle()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo(genericTopic.getText()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo(genericTopic.getAuthor()))))
        ;
    }

    @Test
    public void deleteTopic() throws Exception {
        Mockito.doNothing().when(topicService).delete(Mockito.anyLong());

        mockMvc.perform(get(TopicGetController.DELETE_TOPIC, genericTopic.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TopicGetController.SHOW_TOPICS));
    }

    @Test
    public void getTopic() throws Exception {
        Mockito.doReturn(genericTopic).when(topicService).findByIdOrElseThrow(genericTopic.getId());

        mockMvc.perform(get(TopicGetController.GET_TOPIC, genericTopic.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("title", Matchers.equalTo(genericTopic.getTitle()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("text", Matchers.equalTo(genericTopic.getText()))))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("author", Matchers.equalTo(genericTopic.getAuthor()))))
        ;
    }

    @Test
    public void showTopics() throws Exception {
        Mockito.doReturn(List.of(genericTopic)).when(topicService).findAll();

        mockMvc.perform(get(TopicGetController.SHOW_TOPICS))
                .andExpect(status().isOk())
                .andExpect(model().attribute("topicList", Matchers.hasSize(1)))
        ;
    }
}
