package com.example.backend.Repositories;


import com.example.backend.Model.User.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String userName);

}

