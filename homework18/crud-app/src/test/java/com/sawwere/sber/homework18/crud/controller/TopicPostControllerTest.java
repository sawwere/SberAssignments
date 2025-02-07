package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link TopicPostController}
 */
@WebMvcTest({TopicPostController.class})
public class TopicPostControllerTest {
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
        Mockito.doReturn(genericTopic).when(topicService).findByIdOrElseThrow(genericTopic.getId());
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(topicService);
    }

    @Test
    public void createTopic() throws Exception {
        TopicCreationDto dto = TopicCreationDto.builder()
                .title("title tittle")
                .author("author author")
                .text("text text")
                .build();
        Mockito.doReturn(genericTopic).when(topicService).create(Mockito.any());

        mockMvc.perform(post(TopicPostController.CREATE_TOPIC)
                        .formField("title", dto.getTitle())
                        .formField("author", dto.getAuthor())
                        .formField("text", dto.getText()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateTopic() throws Exception {
        Mockito.doReturn(genericTopic).when(topicService).update(Mockito.any(), Mockito.any());

        mockMvc.perform(put(TopicPostController.UPDATE_TOPIC, 1)
                        .formField("title", genericTopic.getTitle())
                        .formField("author", genericTopic.getAuthor())
                        .formField("text", genericTopic.getText()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
