package com.devjr.taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devjr.taco.cloud.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);

}
