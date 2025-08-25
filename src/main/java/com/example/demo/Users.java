package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class Users {
    private HashMap<String, User> users = new HashMap<>();
    
    public Users(){
        users.put("Alice",new User("Alice",32));
    }
    @GetMapping("/get-User/{name}")
    public User getUser(@PathVariable("name") String name) {
        return users.get(name);
    }

    @PostMapping("/add_user/")
    public String addName(@RequestBody User user){
        users.put(user.getUserName(),user);
        return "User added successfully:"+ user.getUserName() + "with age "+user.getAge(); 
    }
}

