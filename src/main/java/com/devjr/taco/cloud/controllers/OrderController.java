package com.devjr.taco.cloud.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.devjr.taco.cloud.entities.Order;
import com.devjr.taco.cloud.entities.User;
import com.devjr.taco.cloud.repositories.OrderRepository;
import com.devjr.taco.cloud.tools.UtilityConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(UtilityConfig.S_PATH_ORDERS)
@SessionAttributes("order")
public class OrderController{

    @Autowired
    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order){
        if(order.getName() == null){
            order.setName(user.getFullname());
        }
        if(order.getStreet() == null){
            order.setStreet(user.getStreet());
        }
        if(order.getCity() == null){
            order.setCity(user.getCity());
        }
        if(order.getState() == null){
            order.setState(user.getState());
        }
        if(order.getZip() == null){
            order.setZip(user.getZip());
        }
        return UtilityConfig.S_VIEW_ORDER;
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
            @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return UtilityConfig.S_VIEW_ORDER;
        }
        order.setUser(user);
        this.orderRepo.save(order);
        sessionStatus.setComplete();
        log.info("Order submitted: " + order);
        return "redirect:" + UtilityConfig.S_PATH_HOME;
    }

}
