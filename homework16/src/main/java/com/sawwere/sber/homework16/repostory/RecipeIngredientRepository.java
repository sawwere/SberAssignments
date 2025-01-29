package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.RecipeIngredient;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository {
    RecipeIngredient create(RecipeIngredient recipeIngredient);
}
