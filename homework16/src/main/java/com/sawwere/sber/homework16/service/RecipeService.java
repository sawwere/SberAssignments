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


public interface RecipeService {
    Optional<Recipe> findById(Long id);

    Optional<Recipe> findById(Long id, boolean eagerFetch);

    List<Recipe> search(String query);

    Recipe create(Recipe recipe);

    boolean delete(Long recipeId);
}
