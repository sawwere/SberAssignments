package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Ingredient;

import java.util.List;

/**
 * Репозиторий для выполнения операций с ингредиентами в базе данных
 */
public interface IngredientRepository {
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
