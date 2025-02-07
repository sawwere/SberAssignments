package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TopicPostController {
    private final TopicService topicService;

    public static final String CREATE_TOPIC = "/topics";
    public static final String UPDATE_TOPIC = "/topics/{id}";


    @PostMapping(CREATE_TOPIC)
    public ModelAndView createTopic(@Valid @ModelAttribute TopicCreationDto dto) {
        ModelAndView mav = new ModelAndView("topic");
        Topic topic = topicService.create(dto);
        mav.addObject("topic", topic);
        mav.addObject("newReply", new ReplyCreationDto());
        return mav;
    }

    @PutMapping(UPDATE_TOPIC)
    public ModelAndView updateTopic(@PathVariable Long id, @Valid @ModelAttribute TopicCreationDto dto) {
        Topic updatedTopic = topicService.update(id, dto);
        ModelAndView mav = new ModelAndView("topic");
        mav.addObject("topic", updatedTopic);
        mav.addObject("newReply", new ReplyCreationDto());
        return mav;
    }
}
