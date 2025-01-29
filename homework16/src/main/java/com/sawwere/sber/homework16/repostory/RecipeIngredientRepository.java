package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.RecipeIngredient;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для выполнения операций с компонентами рецептов в базе данных
 */
public interface RecipeIngredientRepository {
    /**
     * Создает в базе новую запись о компоненте блюда
     * @param recipeIngredient создаваемый компонент блюда
     * @return созданный компонент блюда
     */
    RecipeIngredient create(RecipeIngredient recipeIngredient);
}
