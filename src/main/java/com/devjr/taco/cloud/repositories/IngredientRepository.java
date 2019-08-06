package com.devjr.taco.cloud.repositories;

import com.devjr.taco.cloud.entities.Ingredient;

public interface IngredientRepository{

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);

}
