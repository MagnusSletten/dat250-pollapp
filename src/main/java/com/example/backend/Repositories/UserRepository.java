package com.example.backend.Repositories;

import org.springframework.stereotype.Repository;

import com.example.backend.Model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findById(); 

    List<User> findAll();
    Optional<User> findByUserName(String userName);

}

