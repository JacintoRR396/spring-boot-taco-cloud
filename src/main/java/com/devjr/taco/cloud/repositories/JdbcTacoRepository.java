package com.devjr.taco.cloud.repositories;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.devjr.taco.cloud.entities.Ingredient;
import com.devjr.taco.cloud.entities.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository{

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco){
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for(Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO " + S_TABLE_TACO + " (" + S_TTACO_NAME + ", " + S_TTACO_CREATEDAT + ") VALUES (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        /*PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "INSERT INTO " + S_TABLE_TACO + " (" + S_TTACO_NAME + ", " + S_TTACO_CREATEDAT + ") VALUES (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(
                        Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));*/
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        this.jdbc.update(
                "INSERT INTO " + S_TABLE_TAING + " (" + S_TTAING_TACO + ", " + S_TTAING_ING + ") VALUES (?, ?)", tacoId,
                ingredient.getId());
    }

}
