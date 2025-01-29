package com.sawwere.sber.homework16.service;

import com.sawwere.sber.homework16.domain.Recipe;
import com.sawwere.sber.homework16.domain.RecipeIngredient;
import com.sawwere.sber.homework16.repostory.RecipeIngredientRepository;
import com.sawwere.sber.homework16.repostory.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Optional<Recipe> findById(Long id, boolean eagerFetch) {
        if (!eagerFetch) {
            return findById(id);
        }
        return recipeRepository.findByIdWithEagerFetch(id);
    }

    @Override
    public List<Recipe> search(String query) {
        return recipeRepository.findAllByLike(query);
    }

    @Override
    @Transactional
    public Recipe create(Recipe recipe) {
        Recipe result = recipeRepository.create(recipe);
        for (RecipeIngredient ri : recipe.getIngredients()) {
            recipeIngredientRepository.create(ri);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean delete(Long recipeId) {
        return recipeRepository.deleteById(recipeId);
    }
}
