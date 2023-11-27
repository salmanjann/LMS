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

    @Override
    public String toString() {
        return getName();
    }

    public String studentApplyCourse(int courseId, int teacherId){
        String findTeacherCourse = "select * from teachercourse where courseid = " + courseId + " and userid = " + teacherId + ";";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(findTeacherCourse);
            try {
                queryResult.next();
                String insertTeacherCourse = "insert into StudentTeacherCourse (userId, teachercourseid, approved) value (" + ApplicationState.currentlyLoggedStudent.getId() + ", " +  queryResult.getString("id")+ "," + " 0);";
                statement.executeUpdate(insertTeacherCourse);
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Already Applied";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "Applied for Course";
    }
}
