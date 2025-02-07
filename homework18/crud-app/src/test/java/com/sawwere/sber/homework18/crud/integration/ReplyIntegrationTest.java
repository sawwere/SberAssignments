package com.sawwere.sber.homework18.crud.integration;

import com.sawwere.sber.homework18.crud.controller.ReplyController;
import com.sawwere.sber.homework18.crud.controller.TopicGetController;
import com.sawwere.sber.homework18.crud.domain.Reply;
import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.servce.ReplyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ReplyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ReplyService replyService;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(replyService);
    }


    @Test
    @Sql(statements = """
            INSERT INTO topics
                (id, title, text, author)
            VALUES
                (10012, 'пост 10012', 'текст', 'no no admin');
            """,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void createReply() throws Exception {
        ReplyCreationDto dto = ReplyCreationDto.builder()
                .author("reply author")
                .text("reply text")
                .build();
        Long topicId = 10012L;

        String expectedRedirectUrl = TopicGetController.GET_TOPIC.replace("{id}", topicId.toString());
        mockMvc.perform(post(ReplyController.CREATE_REPLY, topicId)
                        .formField("topicId", topicId.toString())
                        .formField("author", dto.getAuthor())
                        .formField("text", dto.getText()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedRedirectUrl))
        ;

        mockMvc.perform(get(expectedRedirectUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("topic"))
                .andExpect(model().attribute(
                        "topic",
                        Matchers.hasProperty("replies", Matchers.hasSize(1))))
        ;
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO topics
                (id, title, text, author)
            VALUES
                (10012, 'пост 10012', 'текст', 'no no admin');
            """,
            """
            INSERT INTO replies
                (id, topic_id, text, author)
            VALUES
                 (666, '10012', 'текст ответа', 'автор ответа');
            """},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    public void deleteReply() throws Exception {
        mockMvc.perform(get(ReplyController.DELETE_REPLY, 666))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TopicGetController.SHOW_TOPICS))
                .andDo(print());
        Mockito.verify(replyService, Mockito.only()).delete(Mockito.anyLong());
    }
}
