package com.devjr.taco.cloud.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devjr.taco.cloud.entities.Ingredient;
import com.devjr.taco.cloud.entities.Taco;
import com.devjr.taco.cloud.repositories.IngredientRepository;
import com.devjr.taco.cloud.repositories.OrderRepository;
import com.devjr.taco.cloud.repositories.TacoRepository;
import com.devjr.taco.cloud.tools.UtilityConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest{

    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    private Taco design;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository designRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Before
    public void setup(){
        this.ingredients = Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

        when(this.ingredientRepository.findAll()).thenReturn(this.ingredients);
        when(this.ingredientRepository.findOne("FLTO"))
                .thenReturn(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        when(this.ingredientRepository.findOne("GRBF"))
                .thenReturn(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        when(this.ingredientRepository.findOne("CHED"))
                .thenReturn(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));

        this.design = new Taco();
        this.design.setCreatedAt(new Date());
        this.design.setName("Test Taco");
        this.design.setIngredients(Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE)));
    }

    @Test
    public void testShowDesignForm() throws Exception{
        this.mockMvc.perform(get(UtilityConfig.S_PATH_DESIGN)).andExpect(status().isOk())
                .andExpect(view().name(UtilityConfig.S_VIEW_DESIGN))
                .andExpect(model().attribute(Ingredient.Type.WRAP.toString().toLowerCase(),
                        this.ingredients.subList(0, 2)))
                .andExpect(model().attribute(Ingredient.Type.PROTEIN.toString().toLowerCase(),
                        this.ingredients.subList(2, 4)))
                .andExpect(model().attribute(Ingredient.Type.VEGGIES.toString().toLowerCase(),
                        this.ingredients.subList(4, 6)))
                .andExpect(model().attribute(Ingredient.Type.CHEESE.toString().toLowerCase(),
                        this.ingredients.subList(6, 8)))
                .andExpect(model().attribute(Ingredient.Type.SAUCE.toString().toLowerCase(),
                        this.ingredients.subList(8, 10)));
    }

    @Test
    public void processDesign() throws Exception{
        when(this.designRepository.save(this.design)).thenReturn(this.design);
        this.mockMvc
                .perform(post(UtilityConfig.S_PATH_DESIGN).content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection()).andExpect(header().stringValues("Location", "/orders/current"));
    }

}
