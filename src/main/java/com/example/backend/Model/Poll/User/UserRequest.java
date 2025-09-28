package com.example.backend.Model.Poll.User;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "username"
)
public class UserRequest{
    private String username;
    private String email;
    private Integer userId;

    public User toUser(){
        User user = new User(); 
        user.setEmail(email);
        user.setPolls(new ArrayList<>());
        user.setUsername(username);
        user.setVotes(new ArrayList<>());

        return user; 

    }

}