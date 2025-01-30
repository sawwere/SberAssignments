package com.sawwere.sber.homework17.service;

import com.sawwere.sber.homework17.domain.Ingredient;
import com.sawwere.sber.homework17.domain.Recipe;
import com.sawwere.sber.homework17.repository.RecipeRepository;
import com.sawwere.sber.homework17.repository.specification.RecipeSpecification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Optional<Recipe> findById(Long id, boolean eagerFetch) {
        if (!eagerFetch) {
            return findById(id);
        }
        return recipeRepository.findByIdFetchIngredients(id);
    }

    @Override
    public List<Recipe> search(String query) {
        Specification<Recipe> specification = Specification.allOf();
        if (query != null) {
            specification = specification.and(RecipeSpecification.likeIgnoreCase(query));
        }
        return recipeRepository.findAll(specification);
    }

    @Override
    @Transactional
    public Recipe create(Recipe recipe) {
        // нужно перевести ингредиенты в persistent состояние
        for (var ri : recipe.getIngredients()) {
            ri.setIngredient(entityManager.getReference(Ingredient.class, ri.getIngredient().getId()));
        }
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public boolean delete(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        return true;
    }
}
