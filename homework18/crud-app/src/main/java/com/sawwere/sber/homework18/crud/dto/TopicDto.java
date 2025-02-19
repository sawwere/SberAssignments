package com.sawwere.sber.homework18.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.sawwere.sber.homework18.crud.domain.Topic}
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    @NotNull
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotBlank
    private String author;

    @Builder.Default
    private List<ReplyDto> replies = new ArrayList<>();
}
