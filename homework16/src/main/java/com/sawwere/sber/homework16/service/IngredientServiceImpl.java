package com.sawwere.sber.homework16.service;

import com.sawwere.sber.homework16.domain.Ingredient;
import com.sawwere.sber.homework16.repostory.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    @Transactional
    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.create(ingredient);
    }
}
