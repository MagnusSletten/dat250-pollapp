package com.example.backend.Model;

import lombok.Data;

@Data
public class User{
    private String userName;
    private String email;
    private Integer userId;  
    public User(String userName, String email){
        this.userName = userName;
        this.email = email; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userName.equals(user.userName);  
    }

    @Override
    public int hashCode() {
        return userName.hashCode();  
    }
}
