package com.sawwere.sber.homework18.crud.controller.api;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.dto.TopicDto;
import com.sawwere.sber.homework18.crud.mapper.TopicMapper;
import com.sawwere.sber.homework18.crud.servce.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicRestController {

    private final TopicService topicService;

    public static final String CREATE_TOPIC = "/api/v1/topics";
    public static final String UPDATE_TOPIC = "/api/v1/topics/{id}";
    public static final String DELETE_TOPIC = "/api/v1//topics/{id}";
    public static final String GET_TOPIC = "/api/v1/topics/{id}";
    public static final String GET_ALL_TOPICS = "/api/v1/topics";

    private final TopicMapper topicMapper;

    @GetMapping(GET_ALL_TOPICS)
    public List<TopicDto> getAll() {
        return topicService.findAll().stream().map(topicMapper::mapToDto).toList();
    }

    @GetMapping(GET_TOPIC)
    public TopicDto getOne(@PathVariable(name = "id") Long id) {
        var obj = topicService.findByIdOrElseThrow(id);
        return topicMapper.mapToDto(obj);
    }

    @PostMapping(CREATE_TOPIC)
    @ResponseStatus(HttpStatus.CREATED)
    public TopicDto create(@Valid @RequestBody TopicCreationDto topic) {
        return topicMapper.mapToDto(topicService.create(topic));
    }

    @PutMapping(UPDATE_TOPIC)
    public Topic update(@PathVariable(name = "id") Long id, @Valid @RequestBody TopicCreationDto topic) {
        return topicService.update(id, topic);
    }

    @DeleteMapping(DELETE_TOPIC)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        topicService.delete(id);
    }
}
