package com.sawwere.sber.homework17.domain;

import jakarta.persistence.*;
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
@Entity
@Table(name = "recipes_ingredients")
public class RecipeIngredient {
    @EmbeddedId
    @Builder.Default
    private RecipeIngredientId recipeIngredientId = new RecipeIngredientId();

    @ManyToOne(optional = false)
    @MapsId(value = "recipeId")
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @MapsId(value = "ingredientId")
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "measure_unit", nullable = false)
    private String measureUnit;
}
