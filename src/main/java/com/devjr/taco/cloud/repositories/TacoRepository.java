package com.devjr.taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devjr.taco.cloud.entities.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{

}
