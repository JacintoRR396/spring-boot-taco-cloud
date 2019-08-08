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
        String sql = "SELECT " + S_TING_ID + ", " + S_TING_NAME + ", " + S_TING_TYPE + " FROM " + S_TABLE_ING;
        return this.jdbc.query(sql, this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id){
        String sql = "SELECT " + S_TING_ID + ", " + S_TING_NAME + ", " + S_TING_TYPE + " FROM " + S_TABLE_ING + " WHERE " + S_TING_ID + "=?";
        return this.jdbc.queryForObject(sql, this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient){
        String sql = "INSERT INTO " + S_TABLE_ING + " (" + S_TING_ID + ", " + S_TING_NAME + ", " + S_TING_TYPE + ") VALUES (?, ?, ?)";
        this.jdbc.update(sql, ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    //RowMapper con mapRow()
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException{
        return new Ingredient(rs.getString(S_TING_ID), rs.getString(S_TING_NAME), Ingredient.Type.valueOf(rs.getString(S_TING_TYPE)));
    }

}
