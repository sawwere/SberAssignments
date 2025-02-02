package com.sawwere.sber.homework18.crud.dto;

import lombok.*;

import java.util.List;

/**
 * DTO for {@link com.sawwere.sber.homework18.crud.domain.Topic}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicCreationDto {
    private String title;
    private String text;
    private String author;
}