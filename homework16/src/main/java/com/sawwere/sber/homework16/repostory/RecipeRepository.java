package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    Optional<Recipe> findById(Long id);

    Optional<Recipe> findByIdWithEagerFetch(Long id);

    List<Recipe> findAllByLike(String query);

    Recipe create(Recipe recipe);

    boolean deleteById(Long id);
}
