package com.devjr.taco.cloud.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devjr.taco.cloud.tools.UtilityConfig;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController(UtilityConfig.S_PATH_HOME).setViewName(UtilityConfig.S_VIEW_HOME);
        registry.addViewController(UtilityConfig.S_PATH_LOGIN);
    }

}