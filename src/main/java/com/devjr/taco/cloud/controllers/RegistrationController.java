package com.devjr.taco.cloud.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devjr.taco.cloud.entities.User;
import com.devjr.taco.cloud.repositories.UserRepository;
import com.devjr.taco.cloud.security.RegistrationForm;
import com.devjr.taco.cloud.tools.UtilityConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(UtilityConfig.S_PATH_REGISTER)
public class RegistrationController{

    private UserRepository userRepo; //Adaptarlo para utilizar el servicio
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "user")
    public User user(){
        return new User();
    }

    @GetMapping
    public String registerForm(){
        return UtilityConfig.S_VIEW_REGISTER;
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        User user = form.toUser(this.passwordEncoder);
        this.userRepo.save(user);
        log.info("   --- Saving user: " + user);
        return "redirect:" + UtilityConfig.S_PATH_LOGIN;
    }

    //TODO logout ("/logout")

}
