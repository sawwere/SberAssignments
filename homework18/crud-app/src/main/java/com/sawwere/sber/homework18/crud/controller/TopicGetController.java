package com.sawwere.sber.homework18.crud.controller;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.mapper.TopicMapper;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TopicGetController {
    private final TopicService topicService;

    private final TopicMapper topicMapper;

    public static final String GET_CREATE_TOPIC_PAGE = "/topics/create";
    public static final String GET_UPDATE_TOPIC_PAGE = "/topics/update/{id}";
    public static final String DELETE_TOPIC = "/topics/delete/{id}";
    public static final String GET_TOPIC = "/topics/{id}";
    public static final String SHOW_TOPICS = "/topics";

    @GetMapping(GET_CREATE_TOPIC_PAGE)
    public ModelAndView createTopic() {
        ModelAndView mav = new ModelAndView("topic_creation");
        mav.addObject("newTopic", new TopicCreationDto());
        return mav;
    }

    @GetMapping(GET_UPDATE_TOPIC_PAGE)
    public ModelAndView updateTopic(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("topic_update");
        Topic topic = topicService.findByIdOrElseThrow(id);
        mav.addObject("topic", topic);
        return mav;
    }

    @GetMapping(DELETE_TOPIC)
    public String deleteTopic(@PathVariable Long id) {
        topicService.delete(id);
        return "redirect:/topics";
    }

    @GetMapping(GET_TOPIC)
    public ModelAndView getTopic(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("topic");
        Topic topic = topicService.findByIdOrElseThrow(id);
        mav.addObject("topic", topic);
        mav.addObject("newReply", new ReplyCreationDto());
        return mav;
    }

    @GetMapping(SHOW_TOPICS)
    public ModelAndView showTopics() {
        ModelAndView mav = new ModelAndView("topics");
        List<Topic> topicList = topicService.findAll();
        mav.addObject("topicList", topicList);
        return mav;
    }
}
