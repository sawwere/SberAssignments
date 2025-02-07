package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.servce.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    public static final String CREATE_REPLY = "/topics/{id}/reply";
    public static final String DELETE_REPLY = "/replies/delete/{id}";

    @PostMapping(CREATE_REPLY)
    public String createReply(@PathVariable(name = "id") Long topicId, @Valid @ModelAttribute ReplyCreationDto dto) {
        dto.setTopicId(topicId);
        replyService.create(dto);
        return "redirect:%s".formatted(TopicGetController.GET_TOPIC.replace("{id}", topicId.toString()));
    }

    @GetMapping(DELETE_REPLY)
    public String deleteReply(@PathVariable(name = "id") Long replyId) {
        replyService.delete(replyId);
        return "redirect:%s".formatted(TopicGetController.SHOW_TOPICS);
    }
}
