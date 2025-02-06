package com.sawwere.sber.homework18.terminal.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    /**
     * Строковое представление ошибки
     */
    private String title;
    /**
     * Описание ошибки
     */
    private String description;
}

