package com.sawwere.sber.homework17.repository;

import com.sawwere.sber.homework17.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 *  Репозиторий {@link RecipeRepository} с использованием JpaRepository
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    /**
     * Возвращает рецепт по его id. Выполняет жадную инициализацию, подгружает ингредиенты из смежных таблиц
     *
     * @param id значение id искомого рецепта
     * @return искомый рецепт или {@code Optional.empty}, если таковой отсутствует
     */

    @Query("select r from Recipe r left join fetch r.ingredients where r.id = ?1")
    Optional<Recipe> findByIdFetchIngredients(Long id);
}