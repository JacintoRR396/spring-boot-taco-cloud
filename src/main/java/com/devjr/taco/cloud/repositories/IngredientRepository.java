package com.devjr.taco.cloud.repositories;

import com.devjr.taco.cloud.entities.Ingredient;

public interface IngredientRepository{

    String S_TABLE_ING = "Ingredient";
    String S_TING_ID = "id";
    String S_TING_NAME = "name";
    String S_TING_TYPE = "type";
    int I_TING_ID = 4;
    int I_TING_NAME = 25;
    int I_TING_TYPE = 10;

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);

}
