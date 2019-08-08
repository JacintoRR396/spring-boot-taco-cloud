package com.devjr.taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devjr.taco.cloud.entities.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{

}
