package com.sawwere.sber.homework17.service;

import com.sawwere.sber.homework17.domain.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами Ингредиент - {@link Ingredient}
 */
public interface IngredientService {
    /**
     * Возвращает ингредиент по его id
     * @param id значение id искомого ингредиент
     * @return искомый ингредиент или {@code Optional.empty}, если таковой отсутствует
     */
    Optional<Ingredient> findById(Long id);

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
