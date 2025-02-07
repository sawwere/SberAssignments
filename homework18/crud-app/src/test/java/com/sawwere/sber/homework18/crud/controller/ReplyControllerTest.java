package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Reply;
import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.servce.ReplyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link ReplyController}
 */
@WebMvcTest({ReplyController.class})
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyService replyService;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(replyService);
    }

    @Test
    public void createReply() throws Exception {
        ReplyCreationDto dto = ReplyCreationDto.builder()
                .author("reply author")
                .text("reply text")
                .build();
        Long topicId = 1002L;
        Reply reply = Reply.builder()
                .author("author")
                .text("text")
                .build();


        Mockito.doReturn(reply).when(replyService).create(Mockito.any());

        mockMvc.perform(post(ReplyController.CREATE_REPLY, topicId)
                        .formField("topicId", topicId.toString())
                        .formField("author", dto.getAuthor())
                        .formField("text", dto.getText()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TopicGetController.GET_TOPIC.replace("{id}", topicId.toString())))
        ;
    }

    @Test
    public void deleteReply() throws Exception {
        mockMvc.perform(get(ReplyController.DELETE_REPLY, "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TopicGetController.SHOW_TOPICS))
                .andDo(print());
    }
}
