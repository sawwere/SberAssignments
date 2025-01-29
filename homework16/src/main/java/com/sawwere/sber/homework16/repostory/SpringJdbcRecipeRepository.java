package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Ingredient;
import com.sawwere.sber.homework16.domain.Recipe;
import com.sawwere.sber.homework16.domain.RecipeIngredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SpringJdbcRecipeRepository implements RecipeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Recipe> rowMapper = new RecipeRowMapper();

    public SpringJdbcRecipeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("recipes")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Optional<Recipe> findById(Long id) {
        var queryResult = jdbcTemplate.query("""
                SELECT id, name from recipes
                WHERE id = ?""",
                rs -> {
                    if (rs.next()) {
                        return rowMapper.mapRow(rs, rs.getRow());
                    } else {
                        return null;
                    }
                },
                id);
        return Optional.ofNullable(queryResult);
    }

    @Override
    public Optional<Recipe> findByIdWithEagerFetch(Long id) {
        var queryResult = jdbcTemplate.query("""
                        SELECT r.id as id, ri.ingredient_id, r.name as name,\
                            ing.name as ingredient_name, ri.quantity, ri.measure_unit FROM recipes r
                        LEFT JOIN recipes_ingredients ri ON (r.id = ri.recipe_id)
                        LEFT JOIN ingredients ing ON (ri.ingredient_id = ing.id)
                        WHERE r.id = ?;
                        """,
                rs -> {
                    if (rs.next()) {
                        Recipe recipe = rowMapper.mapRow(rs, rs.getRow());
                        recipe.setIngredients(new ArrayList<>());
                        do {
                            long ingredientId = rs.getLong("ingredient_id");
                            if (ingredientId == 0L) {
                                continue;
                            }
                            String ingredientName = rs.getString("ingredient_name");
                            Integer quantity = rs.getInt("quantity");
                            String measureUnit = rs.getString("measure_unit");

                            recipe.getIngredients().add(
                                    RecipeIngredient.builder()
                                            .recipe(recipe)
                                            .ingredient(Ingredient.builder()
                                                    .id(ingredientId)
                                                    .name(ingredientName)
                                                    .build())
                                            .quantity(quantity)
                                            .measureUnit(measureUnit)
                                            .build()
                            );

                        } while(rs.next());
                        return recipe;
                    } else {
                        return null;
                    }
                },
                id);
        return Optional.ofNullable(queryResult);
    }

    @Override
    public List<Recipe> findAllByLike(String query) {
        return jdbcTemplate.query("SELECT * FROM recipes WHERE name LIKE ?",
                        rowMapper,
                        "%" + query + "%"
        );
    }

    @Override
    public Recipe create(Recipe recipe) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(recipe);
        var result = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        recipe.setId(result);
        return recipe;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM recipes WHERE id = ?", id) != 0;
    }

    private static class RecipeRowMapper implements RowMapper<Recipe> {

        @Override
        public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {

            {
                return Recipe.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .build();
            }
        }
    }
}
