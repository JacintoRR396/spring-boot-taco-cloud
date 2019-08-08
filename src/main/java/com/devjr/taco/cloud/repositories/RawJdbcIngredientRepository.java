package com.devjr.taco.cloud.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.devjr.taco.cloud.entities.Ingredient;

/**
 * Raw implementation of {@link IngredientRepository} for
 * comparison with {@link JdbcIngredientRepository} to illustrate
 * the power of using {@link JdbcTemplate}. 
 * @author habuma
 */
public class RawJdbcIngredientRepository implements IngredientRepository{

    private DataSource dataSource;

    public RawJdbcIngredientRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Ingredient> findAll(){
        List<Ingredient> ingredients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "";
        try{
            connection = dataSource.getConnection();
            sql = "SELECT " + S_TING_ID + ", " + S_TING_NAME + ", " + S_TING_TYPE + " FROM " + S_TABLE_ING;
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            Ingredient ingredient = null;
            while(resultSet.next()){
                ingredient = new Ingredient(resultSet.getString(S_TING_ID), resultSet.getString(S_TING_NAME),
                        Ingredient.Type.valueOf(resultSet.getString(S_TING_TYPE)));
                ingredients.add(ingredient);
            }
        }catch(SQLException e){
            // ??? What should be done here ???
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                }
            }
            if(statement != null){
                try{
                    statement.close();
                }catch(SQLException e){
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                }
            }
        }
        return ingredients;
    }

    // tag::rawfindOne[]
    @Override
    public Ingredient findOne(String id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "";
        try{
            connection = dataSource.getConnection();
            sql = "SELECT " + S_TING_ID + ", " + S_TING_NAME + ", " + S_TING_TYPE + " FROM " + S_TABLE_ING;
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            Ingredient ingredient = null;
            if(resultSet.next()){
                ingredient = new Ingredient(resultSet.getString(S_TING_ID), resultSet.getString(S_TING_NAME),
                        Ingredient.Type.valueOf(resultSet.getString(S_TING_TYPE)));
            }
            return ingredient;
        }catch(SQLException e){
            // ??? What should be done here ???
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                }
            }
            if(statement != null){
                try{
                    statement.close();
                }catch(SQLException e){
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                }
            }
        }
        return null;
    }
    // end::rawfindOne[]

    @Override
    public Ingredient save(Ingredient ingredient){
        // TODO: I only needed one method for comparison purposes, so
        //       I've not bothered implementing this one (yet).
        return null;
    }

}
