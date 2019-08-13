package com.devjr.taco.cloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.devjr.taco.cloud.entities.Ingredient;
import com.devjr.taco.cloud.repositories.IngredientRepository;

@SpringBootApplication
public class TacoCloudApplication{

    public static void main(String[] args){
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repoIngredient/*, UserRepository repoUser*/){
        return new CommandLineRunner(){
            @Override
            public void run(String... args) throws Exception{
                repoIngredient.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                repoIngredient.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                repoIngredient.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                repoIngredient.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                repoIngredient.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                repoIngredient.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                repoIngredient.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                repoIngredient.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                repoIngredient.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                repoIngredient.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

                /*repoUser.save(new User("jacinto", "1234", "Jacinto RR", "Corredera", "Sevilla", "Espanha", "41520",
                        "610972145"));*/
            }
        };
    }

}
