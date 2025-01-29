package com.sawwere.sber.homework16.domain;

import lombok.*;

/**
 * Сущность компонент рецепта.
 * Служит как промежуточное звено между рецептами и и их ингредиентами,
 * обеспечивая связь многие-ко-многим.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    private Recipe recipe;
    private Ingredient ingredient;
    private Integer quantity;
    @Builder.Default
    private String measureUnit = "";
}
