package com.example.demo;

public class User {
    private String name;
    private String username;
    private String email;
    private String password;

    public User(String _name,String _username, String _email, String _password){
        name = _name;
        username = _username;
        email = _email;
        password = _password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    // Main functions
    public  void login(){

    }
}
