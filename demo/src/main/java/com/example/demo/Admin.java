package com.example.demo;

import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends User{
    public Admin(){
        super();
    }
    // No attributes now
    public  Admin(int _id, String _name,String _username, String _email, String _password){
        super(_id, _name,_username,_email,_password);
    }
    public String addTeacher(String _name, String _email, String _username, String _password){
        String isTeacherExist = "SELECT COUNT(1) FROM `TEACHER` WHERE username = '" + _username + "' OR email = '" + _email + "'";
        String insertTeacher = "insert into `TEACHER`(`name`, username,email,`password`) value ('"+_name+"','"+_username+"','" + _email + "','"+_password+"');";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isTeacherExist);



            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return "Teacher Exists";
                }
                else {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    statement2.executeUpdate(insertTeacher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "New Teacher Added";
    }

    public String assignCourseToTeacher(int courseId, int teacherId){
        String insertTeacherCourse = "insert into TeacherCourse (courseId, userId) value (" + courseId + ", " + teacherId + ");";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            statement.executeUpdate(insertTeacherCourse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "Course Assigned to Teacher";
    }
}
