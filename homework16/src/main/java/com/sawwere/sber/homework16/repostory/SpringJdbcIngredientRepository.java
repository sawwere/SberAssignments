package com.sawwere.sber.homework16.repostory;

import com.sawwere.sber.homework16.domain.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SpringJdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Ingredient> rowMapper = new IngredientRowMapper();

    public SpringJdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ingredients")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM ingredients",
                        rowMapper
        );
    }

    @Override
    public Ingredient create(Ingredient ingredient) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(ingredient);
        var result = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        ingredient.setId(result);
        return ingredient;
    }

    private static class IngredientRowMapper implements RowMapper<Ingredient> {

        @Override
        public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Ingredient.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
