package com.sawwere.sber.homework18.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for {@link com.sawwere.sber.homework18.crud.domain.Reply}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreationDto {
    @NotNull
    private Long topicId;
    @NotBlank
    private String text;
    @NotBlank
    private String author;
}
