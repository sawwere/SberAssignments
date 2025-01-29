package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.RecipeIngredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Реализация {@link RecipeIngredientRepository} с использованием SpringJdbc
 */
@Repository
public class SpringJdbcRecipeIngredientRepository implements RecipeIngredientRepository {
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SpringJdbcRecipeIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("recipes_ingredients");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecipeIngredient create(RecipeIngredient recipeIngredient) {
        simpleJdbcInsert.execute(Map.of(
                "recipe_id", recipeIngredient.getRecipe().getId(),
                "ingredient_id", recipeIngredient.getIngredient().getId(),
                "quantity", recipeIngredient.getQuantity(),
                "measure_unit", recipeIngredient.getMeasureUnit()
                )
        );
        return recipeIngredient;
    }
}
