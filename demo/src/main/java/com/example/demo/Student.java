package com.example.demo;

public class Student extends  User{
    private  String rollNo;
    public Student(){
        super();
        rollNo = "";
    }
    public  Student(String _name,String _username, String _email, String _password,String _rollNo){
        super(_name,_username,_email,_password);
        rollNo = _rollNo;
    }
}
