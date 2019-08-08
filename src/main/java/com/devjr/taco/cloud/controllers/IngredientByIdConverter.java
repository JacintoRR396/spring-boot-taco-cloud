package com.devjr.taco.cloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.devjr.taco.cloud.entities.Ingredient;
import com.devjr.taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient>{

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id){
        return ingredientRepo.findOne(id);
    }

}
