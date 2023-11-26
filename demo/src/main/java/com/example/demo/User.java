package com.example.demo;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    public User(){
        name = username = email = password = "";
    }

    public User(int _id, String _name,String _username, String _email, String _password){
        id = _id;
        name = _name;
        username = _username;
        email = _email;
        password = _password;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
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
