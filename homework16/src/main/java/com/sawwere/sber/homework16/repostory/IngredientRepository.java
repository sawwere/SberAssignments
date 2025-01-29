package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> findAll();

    Ingredient create(Ingredient ingredient);
}
