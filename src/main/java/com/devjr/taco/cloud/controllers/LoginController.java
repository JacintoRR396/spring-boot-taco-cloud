package com.devjr.taco.cloud.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devjr.taco.cloud.entities.User;
import com.devjr.taco.cloud.repositories.UserRepository;
import com.devjr.taco.cloud.tools.UtilityConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(UtilityConfig.S_PATH_LOGIN)
public class LoginController{

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "user")
    public User user(){
        return new User();
    }

    @GetMapping
    public String loginForm(){
        System.out.println("Controlador maneja el GET");
        return UtilityConfig.S_VIEW_LOGIN;
    }

    @PostMapping
    public String processLogin(@Valid User user, Errors errors){
        System.out.println("Controlador maneja el POST : NO PASAAAAAAAAAAA");
        if(errors.hasErrors()){
            return UtilityConfig.S_VIEW_LOGIN;
        }else{
            log.info("   --- Login user: ");
            return "redirect:" + UtilityConfig.S_PATH_DESIGN;
        }
    }

}
