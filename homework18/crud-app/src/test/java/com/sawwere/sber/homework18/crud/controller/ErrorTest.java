package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.domain.exception.NotFoundException;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest({TopicGetController.class, TopicPostController.class})
public class ErrorTest {
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
    public void testGenericExceptionHandler() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(topicService).findByIdOrElseThrow(Mockito.anyLong());

        mockMvc.perform(get(TopicGetController.GET_TOPIC, genericTopic.getId()))
                .andExpect(status().is5xxServerError())
                .andExpect(model().attributeExists("errorInfo"))
        ;
    }

    @Test
    public void testNotFoundExceptionHandler() throws Exception {
        Mockito.doThrow(new NotFoundException()).when(topicService).findByIdOrElseThrow(Mockito.anyLong());

        mockMvc.perform(get(TopicGetController.GET_TOPIC, 666))
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("errorInfo"))
        ;
    }

    @Test
    public void testMethodArgumentNotValidExceptionHandler() throws Exception {
        TopicCreationDto dto = TopicCreationDto.builder()
                .title("    ")
                .author("")
                .text("text text")
                .build();
        Mockito.doReturn(genericTopic).when(topicService).create(Mockito.any());

        mockMvc.perform(post(TopicPostController.CREATE_TOPIC)
                        .formField("title", dto.getTitle())
                        .formField("author", dto.getAuthor())
                        .formField("text", dto.getText()))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("errorInfo"))
        ;
    }
}
