package com.sawwere.sber.homework18.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreationDto {
    @JsonProperty(value = "topic_id")
    private Long topicId;
    private String text;
    private String author;
}
