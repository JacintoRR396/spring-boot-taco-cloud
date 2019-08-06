package com.devjr.taco.cloud.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.devjr.taco.cloud.entities.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll(){
        return this.jdbc.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id){
        return this.jdbc.queryForObject("SELECT id, name, type FROM Ingredient WHERE id=?", this::mapRowToIngredient,
                id);
    }

    @Override
    public Ingredient save(Ingredient ingredient){
        jdbc.update("INSERT INTO Ingredient (id, name, type) VALUES (?, ?, ?)", ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    //RowMapper con mapRow()
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException{
        return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
    }

}
