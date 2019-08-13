package com.devjr.taco.cloud.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.devjr.taco.cloud.entities.Ingredient;
import com.devjr.taco.cloud.entities.Order;
import com.devjr.taco.cloud.entities.Taco;
import com.devjr.taco.cloud.entities.User;
import com.devjr.taco.cloud.repositories.IngredientRepository;
import com.devjr.taco.cloud.repositories.TacoRepository;
import com.devjr.taco.cloud.repositories.UserRepository;
import com.devjr.taco.cloud.tools.UtilityConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(UtilityConfig.S_PATH_DESIGN)
@SessionAttributes("order")
public class DesignTacoController{

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;
    private UserRepository userRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo,
            UserRepository userRepo){
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = designRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    //@RequestMapping(method=RequestMethod.GET)
    public String showDesignForm(Model model, Principal principal){
        log.info("   --- Designing taco");
        List<Ingredient> ingredients = new ArrayList<>();
        this.ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);
        return UtilityConfig.S_VIEW_DESIGN;
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type){
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
        log.info("   --- Saving taco: " + design);
        if(errors.hasErrors()){
            return UtilityConfig.S_VIEW_DESIGN;
        }
        Taco saved = this.tacoRepo.save(design);
        order.addDesign(saved);
        return "redirect:" + UtilityConfig.S_PATH_ORDER;
    }

}
