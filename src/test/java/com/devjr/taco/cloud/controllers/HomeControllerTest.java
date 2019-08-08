package com.devjr.taco.cloud.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devjr.taco.cloud.repositories.IngredientRepository;
import com.devjr.taco.cloud.repositories.OrderRepository;
import com.devjr.taco.cloud.repositories.TacoRepository;
import com.devjr.taco.cloud.tools.UtilityConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository designRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testHome() throws Exception{
        this.mockMvc.perform(get(UtilityConfig.S_PATH_HOME)).andExpect(status().isOk())
                .andExpect(view().name(UtilityConfig.S_VIEW_HOME))
                .andExpect(content().string(containsString("Welcome to...")));
    }

}
