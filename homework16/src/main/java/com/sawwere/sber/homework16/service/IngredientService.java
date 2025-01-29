package com.sawwere.sber.homework16.service;

import com.sawwere.sber.homework16.domain.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient create(Ingredient ingredient);
}
