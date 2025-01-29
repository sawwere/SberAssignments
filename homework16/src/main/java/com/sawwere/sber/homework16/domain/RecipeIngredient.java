package com.sawwere.sber.homework16.domain;

import lombok.*;

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
