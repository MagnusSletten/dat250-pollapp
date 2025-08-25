package com.example.demo.Model;

public class User{
    private String userName;
    private Integer age; 
    public User(String userName, Integer age){
        this.userName = userName;
        this.age = age; 
    }
        public User() {
    }

    public String getUserName() {
        return userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(Integer age) {
        this.age = age;
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
