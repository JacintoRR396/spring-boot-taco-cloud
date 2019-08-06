package com.devjr.taco.cloud.repositories;

import com.devjr.taco.cloud.entities.Order;

public interface OrderRepository{

    Order save(Order order);

}
