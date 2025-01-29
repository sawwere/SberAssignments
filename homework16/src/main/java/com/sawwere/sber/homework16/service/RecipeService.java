package com.sawwere.sber.homework16.service;

import com.sawwere.sber.homework16.domain.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с объектами Рецепт - {@link Recipe}
 */
public interface RecipeService {
    /**
     * Возвращает рецепт по его id
     * @param id значение id искомого рецепта
     * @return искомый рецепт или {@code Optional.empty}, если таковой отсутствует
     */
    Optional<Recipe> findById(Long id);

    /**
     * Возвращает рецепт по его id. Опционально подгружает связанные сущности из других таблиц
     * @param id id значение id искомого рецепта
     * @param eagerFetch если {@code true}, будет выполнена жадная инициализация
     * @return искомый рецепт или {@code Optional.empty}, если таковой отсутствует
     */
    Optional<Recipe> findById(Long id, boolean eagerFetch);

    /**
     * Возвращает список рецептов, название которых содержит в себе заданную подстроку.
     * @param query подстрока, использующаяся для поиска
     * @return новый список
     */
    List<Recipe> search(String query);

    /**
     * Создает в базе новую запись о рецепте
     * @param recipe создаваемый рецепт
     * @return созданный рецепт
     */
    Recipe create(Recipe recipe);

    /**
     * Удаляет рецепт по его id
     * @param recipeId значение id удаляемого рецепта
     * @return {@code true}, если рецепт был удален, иначе = {@code false}
     */
    boolean delete(Long recipeId);
}
