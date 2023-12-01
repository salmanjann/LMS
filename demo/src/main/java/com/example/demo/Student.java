package com.example.demo;

import java.sql.ResultSet;
import java.sql.Statement;

public class Student extends  User{
    private  String rollNo;
    public Student(){
        super();
        rollNo = "";
    }
    public  Student(int _id, String _name,String _username, String _email, String _password,String _rollNo){
        super(_id, _name,_username,_email,_password);
        rollNo = _rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void requestCourse(String courseName){
        String query = "insert into requestingCourse(studentName,courseName) value('"+ApplicationState.currentlyLoggedStudent.getRollNo()+"','"+courseName+"');";
        try{
            Statement statement = ApplicationState.connectDB.createStatement();
            statement.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
