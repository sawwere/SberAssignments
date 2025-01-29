package com.sawwere.sber.homework16.domain;

import lombok.*;

/**
 * Сущность Ингредиент
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private Long id;
    private String name;
}
