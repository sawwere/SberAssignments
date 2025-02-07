package com.sawwere.sber.homework18.crud.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String author;
}