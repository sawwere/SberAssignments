package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для выполнения операций с рецептами в базе данных
 */
public interface RecipeRepository {
    /**
     * Возвращает рецепт по его id
     * @param id значение id искомого рецепта
     * @return искомый рецепт или {@code Optional.empty}, если таковой отсутствует
     */
    Optional<Recipe> findById(Long id);

    /**
     * Возвращает рецепт по его id. Выполняет жадную инициализацию, подгружает поля из смежных таблиц
     * @param id значение id искомого рецепта
     * @return искомый рецепт или {@code Optional.empty}, если таковой отсутствует
     */
    Optional<Recipe> findByIdWithEagerFetch(Long id);

    /**
     * Возвращает список рецептов, название которых содержит в себе заданную подстроку.
     * @param query подстрока, использующаяся для поиска
     * @return новый список
     */
    List<Recipe> findAllByLike(String query);

    /**
     * Создает в базе новую запись о рецепте
     * @param recipe создаваемый рецепт
     * @return созданный рецепт
     */
    Recipe create(Recipe recipe);

    /**
     * Удаляет рецепт по его id
     * @param id значение id удаляемого рецепта
     * @return {@code true}, если рецепт был удален, иначе = {@code false}
     */
    boolean deleteById(Long id);
}
