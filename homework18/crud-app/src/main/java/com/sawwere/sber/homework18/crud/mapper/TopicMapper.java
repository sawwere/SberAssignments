package com.sawwere.sber.homework18.crud.mapper;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.dto.TopicDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TopicMapper {
    Topic mapToEntity(TopicCreationDto topicCreationDto);

    Topic mapToEntity(TopicDto topicDto);

    TopicDto mapToDto(Topic topic);

    @AfterMapping
    default void linkReplies(@MappingTarget Topic topic) {
        topic.getReplies().forEach(reply -> reply.setTopic(topic));
    }
}