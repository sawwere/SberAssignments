package com.sawwere.sber.homework16.domain;

import lombok.*;

import java.util.List;

/**
 * Сущность Рецепт
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private Long id;
    private String name;
    private List<RecipeIngredient> ingredients;
}
