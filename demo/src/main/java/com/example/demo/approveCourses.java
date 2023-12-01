package com.example.demo;

import javafx.scene.control.Button;

public class approveCourses {
    private String studentName;
    private String courseName;
    private Button button;

    public approveCourses(String _studentName,String _courseName,Button _button){
        studentName = _studentName;
        courseName = _courseName;
        button = _button;
    }

    public String getCourseName() {
        return courseName;
    }

    public Button getButton() {
        return button;
    }

    public String getStudentName() {
        return studentName;
    }
}
