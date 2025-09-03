package com.example.backend.Model;

public class User{
    private String userName;
    private String email; 
    public User(String userName, String email){
        this.userName = userName;
        this.email = email; 
    }
        public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
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
