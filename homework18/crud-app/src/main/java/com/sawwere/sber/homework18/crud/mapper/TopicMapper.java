package com.sawwere.sber.homework18.crud.mapper;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TopicMapper {
    public Topic mapToEntity(TopicCreationDto dto) {
        return Topic.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .author(dto.getAuthor())
                .replies(new ArrayList<>())
                .build();
    }
}
