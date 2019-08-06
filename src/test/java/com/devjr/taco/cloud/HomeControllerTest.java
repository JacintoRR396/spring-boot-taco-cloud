package com.devjr.taco.cloud;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.devjr.taco.cloud.controllers.HomeController;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHome() throws Exception{
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")));
    }

}
