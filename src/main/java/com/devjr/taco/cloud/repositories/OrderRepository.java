package com.devjr.taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devjr.taco.cloud.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
