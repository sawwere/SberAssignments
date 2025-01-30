package com.sawwere.sber.homework17.repository.specification;

import com.sawwere.sber.homework17.domain.Recipe;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class RecipeSpecification {
    private RecipeSpecification() {}

    public static Specification<Recipe> likeIgnoreCase(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        ("%" + text + "%").toLowerCase(Locale.ROOT)
                );
    }
}
