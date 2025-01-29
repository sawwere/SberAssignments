package com.sawwere.sber.homework16.service;

import com.sawwere.sber.homework16.domain.Ingredient;
import com.sawwere.sber.homework16.domain.Recipe;

import java.util.List;

/**
 * Сервис для работы с объектами Ингредиент - {@link Ingredient}
 */
public interface IngredientService {
    /**
     * Возвращает список всех ингредиентов
     * @return новый список
     */
    List<Ingredient> findAll();

    /**
     * Создает в базе новую запись об ингредиенте
     * @param ingredient создаваемый ингредиент
     * @return созданный ингредиент
     */
    Ingredient create(Ingredient ingredient);
}
