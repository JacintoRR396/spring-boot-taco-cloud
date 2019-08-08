package com.devjr.taco.cloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.devjr.taco.cloud.tools.UtilityConfig;

@Controller
public class HomeController{

    @GetMapping(UtilityConfig.S_PATH_HOME)
    public String home(){
        return UtilityConfig.S_VIEW_HOME;
    }

}