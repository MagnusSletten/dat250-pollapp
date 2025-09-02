package com.example.backend.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; 


public class Users {
    private int curr = 0; 
    private List<User> users; 
   

    public Users(){
        this.users = new ArrayList<>(); 
    }
    

    public void addUser(User User){
        users.add(User);
    }

}
