package com.sawwere.sber.homework17.service;

import com.sawwere.sber.homework17.domain.Ingredient;
import com.sawwere.sber.homework17.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    @Transactional
    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
