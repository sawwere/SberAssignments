package com.sawwere.sber.homework17.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RecipeIngredientId {
    private Long recipeId;

    private Long ingredientId;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecipeIngredientId that)) return false;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }
}
