package com.example.demo;

public class Teacher extends User{
    // No attributes now
    public Teacher(){
        super();
    }
    public  Teacher(int _id, String _name,String _username, String _email, String _password){
        super(_id, _name,_username,_email,_password);
    }

    @Override
    public String toString() {
        return getName();
    }
}
