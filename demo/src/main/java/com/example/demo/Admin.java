package com.example.demo;

public class Admin extends User{
    public Admin(){
        super();
    }
    // No attributes now
    public  Admin(String _name,String _username, String _email, String _password){
        super(_name,_username,_email,_password);
    }
    public void addTeacher(Teacher teacher){

    }
}
