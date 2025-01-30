package com.sawwere.sber.homework17.repository;

import com.sawwere.sber.homework17.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Репозиторий {@link IngredientRepository} с использованием JpaRepository
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}